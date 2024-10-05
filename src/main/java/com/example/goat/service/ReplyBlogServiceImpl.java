package com.example.goat.service;

import com.example.goat.dto.*;
import com.example.goat.entity.Account;
import com.example.goat.entity.Blog;
import com.example.goat.entity.ReplyBlog;
import com.example.goat.repository.AccountRepository;
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

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class ReplyBlogServiceImpl implements ReplyBlogService {

    private final ReplyBlogRepository repository;
    private final BlogRepository blogRepository;
    private final AccountRepository accountRepository;
    private ModelMapper mapper = new ModelMapper();

    @Override
    public void register(ReplyBlogDTO dto, Principal principal) {
        Account account = accountRepository.findByEmail(principal.getName());




        Blog blog = blogRepository.findById(dto.getBlog_num()).get();


        ReplyBlog replyBlog = mapper.map(dto, ReplyBlog.class);

        replyBlog.setAccount(account);
        replyBlog.setBlog(blog);
        repository.save(replyBlog);

    }

    @Override
    public List<ReplyBlogDTO> selectAll() {
        List<ReplyBlog> replyBlogList = repository.findAll();
        replyBlogList.stream().map(abc -> mapper.map(abc, ReplyBlogDTO.class)).collect(Collectors.toList());
        return null;
    }



    @Override
    public void delete(Long num) {
        log.info(num);
        repository.deleteById(num);
    }




    @Override
    public ReplyBlogDTO detale(Long num) {
        return null;
    }

    @Override
    public RPageResponseDTO<ReplyBlogDTO> list(RPageRequestDTO rPageRequestDTO, Long blog_num) {
        String[] types = rPageRequestDTO.getTypes();
        log.info("서비스에서 변환된 : " + Arrays.toString(types));
        String keyword = rPageRequestDTO.getKeyword();
        Pageable pageable = rPageRequestDTO.getPageable("num");
        Page<ReplyBlog> replyBlogPage = repository.searchAllRe(types, keyword, pageable, blog_num);

        //보드 타입의 리스트가 >> 보드 DTO 타입의 리스트로 변환
        List<ReplyBlogDTO> DTOList =
                replyBlogPage.getContent().stream().map(
                        replyBlog -> mapper.map(replyBlog, ReplyBlogDTO.class).setAccountDTO( mapper.map( replyBlog.getAccount()  , AccountDTO.class )  )   ).collect(Collectors.toList());

//        blogDTOList.forEach(blogDTO -> log.info(blogDTO));

        return RPageResponseDTO.<ReplyBlogDTO>withAll()
                .rPageRequestDTO(rPageRequestDTO)
                .dtoList(DTOList)
                .total((int) replyBlogPage.getTotalElements())
                .build();
    }

    @Override
    public void modify(ReplyBlogDTO dto) {
        log.info(dto + "서비스");
        ReplyBlog replyBlog = repository.findById(dto.getNum()).orElseThrow(EntityNotFoundException::new);
        replyBlog.setContent(dto.getContent());
    }
    //    @Override
//    public void delete(Long num) {
//        repository.deleteById(num);
//    }


//
//    private final ReplyBlogRepository repository;
//    private ModelMapper mapper = new ModelMapper();
//
//    @Override
//    public void register(ReplyBlogDTO dto) {
//        //등록 맵퍼로 맵핑해서 블로그 레파지토리에 세이브함
//
//        Blog blog = mapper.map(dto, Blog.class);
//        repository.save(blog);
//    }
//
//    @Override
//    public List<ReplyBlogDTO> selectAll() {
//        List<Blog> boardList = repository.findAll();
//        boardList.stream().map(abc -> mapper.map(abc, BlogDTO.class)).collect(Collectors.toList());
//        return null;
//    }
//
//    @Override
//    public void upadate(ReplyBlogDTO dto) {
//        Blog blog = repository.findById(blogDTO.getNum()).orElseThrow(EntityNotFoundException::new);
//        blog.setContent(blogDTO.getContent());
//    }
//
//    @Override
//    public void delete(Long num) {
//        repository.deleteById(num);
//    }
//
//    @Override
//    public ReplyBlogDTO detale(Long num) {
//        Blog blog = repository.findById(num).get();
//        BlogDTO blogDTO = mapper.map(blog , BlogDTO.class);
//        return blogDTO;
//    }
//
//    @Override
//    public PageResponseDTO<ReplyBlogDTO> list(PageRequestDTO pageRequestDTO) {
//        String[] types = pageRequestDTO.getTypes();
//        log.info("서비스에서 변환된 : "+ Arrays.toString(types));
//        String keyword = pageRequestDTO.getKeyword();
//        Pageable pageable = pageRequestDTO.getPageable("num");
//        Page<Blog> blogPage = repository.searchAll(types, keyword, pageable);
//
//        //보드 타입의 리스트가 >> 보드 DTO 타입의 리스트로 변환
//        List<BlogDTO> blogDTOList =
//                blogPage.getContent().stream().map(
//                        blog -> mapper.map(blog,BlogDTO.class)).collect(Collectors.toList());
//
////        blogDTOList.forEach(blogDTO -> log.info(blogDTO));
//
//        return PageResponseDTO.<BlogDTO>withAll()
//                .pageRequestDTO(pageRequestDTO)
//                .dtoList(blogDTOList)
//                .total((int) blogPage.getTotalElements())
//                .build();
//    }
//
//    @Override
//    public void modify(ReplyBlogDTO blogDTO) {
//
//        Blog blog = repository.findById(blogDTO.getNum()).orElseThrow(EntityNotFoundException::new);
//        blog.setContent(blogDTO.getContent());
//        blog.setTitle(blogDTO.getTitle());
//        blog.setCompanion(blogDTO.getCompanion());
//        blog.setSchedule(blogDTO.getSchedule());
//    }
}
