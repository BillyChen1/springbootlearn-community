package cn.billychen.community.service;

import cn.billychen.community.dto.PaginationDTO;
import cn.billychen.community.dto.QuestionDTO;
import cn.billychen.community.mapper.QuestionMapper;
import cn.billychen.community.mapper.UserMapper;
import cn.billychen.community.model.Question;
import cn.billychen.community.model.QuestionExample;
import cn.billychen.community.model.User;
import cn.billychen.community.model.UserExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//初始service层
//需求：根据question对象中的creator去查找user表得到头像
//但是不能在一个mapper类里同时依赖两张表
//当我需要同时组装，联系两个实体对象时，需要一个中间层去做这件事情
@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    public PaginationDTO list(Integer page, Integer size) {
        //表示数据库记录的偏移起始点，即该页的第一条记录编号
        int offset = size * (page - 1);
        List<Question> questionList = questionMapper.selectByExampleWithRowbounds(new QuestionExample(), new RowBounds(offset, size));
        List<QuestionDTO>  questionDTOList = new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();

        for (Question question : questionList) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        //将该页的问题列表放入页信息对象中
        paginationDTO.setQuestions(questionDTOList);
        //拿到问题的总数
        Integer totalCount = (int)questionMapper.countByExample(new QuestionExample());
        //在PaginationDTO类的逻辑中设置一些其他的信息
        paginationDTO.setPagination(totalCount, page, size);

        return paginationDTO;
    }

    public PaginationDTO list(Integer userId, int page, int size) {
        int offset = size * (page - 1);
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria()
                .andCreatorEqualTo(userId);
        List<Question> questionList = questionMapper.selectByExampleWithRowbounds(questionExample, new RowBounds(offset, size));
        List<QuestionDTO>  questionDTOList = new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();

        for (Question question : questionList) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        //将该页的问题列表放入页信息对象中
        paginationDTO.setQuestions(questionDTOList);
        //拿到某用户发布的问题的总数
        QuestionExample questionExample1 = new QuestionExample();
        questionExample1.createCriteria()
                .andCreatorEqualTo(userId);
        Integer totalCount = (int)questionMapper.countByExample(questionExample1);
        //在PaginationDTO类的逻辑中设置一些其他的信息
        paginationDTO.setPagination(totalCount, page, size);

        return paginationDTO;

    }

    public QuestionDTO getById(Integer id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        //先判断这是否是一个新增的问题 如果id为空说明为新增
        if (question.getId() == null) {
            //创建
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insert(question);
        } else {
            //更新
            question.setGmtModified(System.currentTimeMillis());
            Question updateQuestion = new Question();
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());
            updateQuestion.setGmtModified(System.currentTimeMillis());

            QuestionExample example = new QuestionExample();
            example.createCriteria()
                    .andIdEqualTo(question.getId());
            questionMapper.updateByExampleSelective(updateQuestion, example);
        }
    }
}
