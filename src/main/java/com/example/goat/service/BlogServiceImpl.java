package com.example.goat.service;

import com.example.goat.dto.BlogDTO;
import com.example.goat.dto.PageRequestDTO;
import com.example.goat.dto.PageResponseDTO;
import com.example.goat.entity.Blog;
import com.example.goat.repository.BlogRepository;
import com.example.goat.repository.ReplyBlogRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;
    private final ReplyBlogRepository replyBlogRepository;
    private ModelMapper mapper = new ModelMapper();

    @Override
    public void register(BlogDTO blogDTO) {
        //등록 맵퍼로 맵핑해서 블로그 레파지토리에 세이브함

        log.info(blogDTO);
        Blog blog = mapper.map(blogDTO, Blog.class);

        blogRepository.save(blog);
    }

    @Override
    public List<BlogDTO> selectAll() {
        List<Blog> boardList = blogRepository.findAll();
        boardList.stream().map(abc -> mapper.map(abc, BlogDTO.class)).collect(Collectors.toList());
        return null;
    }

    @Override
    public void upadate(BlogDTO blogDTO) {
        Blog blog = blogRepository.findById(blogDTO.getNum()).orElseThrow(EntityNotFoundException::new);
        blog.setContent(blogDTO.getContent());
    }

    @Override
    public void delete(Long num) {
        Long blog_num = num;
        replyBlogRepository.deleteByBlogNum(blog_num);
        blogRepository.deleteById(num);
    }

    @Override
    public BlogDTO detale(Long num) {
        log.info(num);
        Blog blog = blogRepository.findById(num).get();
        BlogDTO blogDTO = mapper.map(blog , BlogDTO.class);
//        Long countSet = blog.getReadCount()+1;
//        blog.setReadCount(countSet);
        blogRepository.save(blog);
        return blogDTO;
    }

    @Override
    public PageResponseDTO<BlogDTO> list(PageRequestDTO pageRequestDTO) {
        String[] types = pageRequestDTO.getTypes();
        log.info("서비스에서 변환된 : "+ Arrays.toString(types));
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("num");
        Page<Blog> blogPage = blogRepository.searchAll(types, keyword, pageable);

        //보드 타입의 리스트가 >> 보드 DTO 타입의 리스트로 변환
        List<BlogDTO> blogDTOList =
                blogPage.getContent().stream().map(
                        blog -> mapper.map(blog,BlogDTO.class)).collect(Collectors.toList());

//        blogDTOList.forEach(blogDTO -> log.info(blogDTO));

        return PageResponseDTO.<BlogDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(blogDTOList)
                .total((int) blogPage.getTotalElements())
                .build();
    }

    @Override
    public void modify(BlogDTO blogDTO) {

        Blog blog = blogRepository.findById(blogDTO.getNum()).orElseThrow(EntityNotFoundException::new);
        blog.setContent(blogDTO.getContent());
        blog.setTitle(blogDTO.getTitle());
        blog.setCompanion(blogDTO.getCompanion());
        blog.setSchedule(blogDTO.getSchedule());
    }


    @Override
    public Blog countappend(Long num) {
        //읽을 본문을 읽어와서 엔티티매니저가 관리할수 있도록 한다.
        Blog blog =  blogRepository.findById(num).orElseThrow(EntityNotFoundException::new);


        blog.setReadCount(blog.getReadCount()+1);

        return blog;
    }
}
