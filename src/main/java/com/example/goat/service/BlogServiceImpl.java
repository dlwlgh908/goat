package com.example.goat.service;

import com.example.goat.dto.*;
import com.example.goat.entity.*;
import com.example.goat.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.Mapping;

import javax.xml.transform.Source;
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
    private final VoteRepository voteRepository;
    private final CityRepository cityRepository;
    private final PlaceRepository placeRepository;
    private ModelMapper mapper = new ModelMapper();

    private final AccountRepository accountRepository;


    @Override
    public Long register(BlogDTO blogDTO, UserDetails user) {
        //등록 맵퍼로 맵핑해서 블로그 레파지토리에 세이브함
        log.info("유저 찍히는지"+user);
        Account account = accountRepository.findByEmail(user.getUsername());
        City city = cityRepository.findByCno(blogDTO.getCnum());
//        blogDTO.setEmail(user.getUsername()); XXXX
        log.info(blogDTO);
        Blog blog = mapper.map(blogDTO, Blog.class);

        blog.setAccount(account);
        blog.setCity(city);
        Blog blog1 = blogRepository.save(blog);
        return blog1.getNum();
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
        voteRepository.deleteByBlogNum(blog_num);
        replyBlogRepository.deleteByBlogNum(blog_num);
        blogRepository.deleteById(num);
    }

    @Override
    public BlogDTO detale(Long num) {
        log.info(num);
        Blog blog = blogRepository.findById(num).get();
        //블러그 엔티티 >> 변수명이 같고 // 설정에 따라서 변수명만 같아도 타입이 다르더라도 값이 변경된다.
        BlogDTO blogDTO = mapper.map(blog , BlogDTO.class).setAccountDTO(  mapper.map(  blog.getAccount() , AccountDTO.class )  );
        // readcount

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
        blog.setSchedule(blogDTO.getSchedule());
    }


    @Override
    public Blog countappend(Long num) {
        //읽을 본문을 읽어와서 엔티티매니저가 관리할수 있도록 한다.
        Blog blog =  blogRepository.findById(num).orElseThrow(EntityNotFoundException::new);


        blog.setReadCount(blog.getReadCount()+1);

        return blog;
    }

    @Override
    public void vote(Long num, UserDetails user, VoteDTO voteDTO) {

        Account account = accountRepository.findByEmail(user.getUsername());
        Blog blog = blogRepository.findById(num).get();

//        voteDTO.setEmail(user.getUsername());
//        voteDTO.setBlog_num(num);


        Vote vote = new Vote();
        vote.setBlog(blog);
        vote.setAccount(account);
        blog.setRecommendCount(blog.getRecommendCount()+1);


        voteRepository.save(vote);

    }

    @Override
    public List<BlogDTO> mainRankBlog(BlogDTO blogDTO) {



        List<BlogDTO>  list =  blogRepository.mainBlogRank();
        for (BlogDTO dto : list) {
            log.info("블러그디티오 pk값 : "+dto.getNum());
            List<Place> aa = placeRepository.findByBlogNum(dto.getNum());
            List<PlaceDTO> bb =
                    aa.stream().map( place -> mapper.map( place, PlaceDTO.class).setCityDTO( mapper.map(place.getCity(), CityDTO.class) )).collect(Collectors.toList());
            dto.setPlaceDTOList(bb);
        }
        list.forEach(blogDTO1 ->  log.info(blogDTO1));

        return list;

    }

//    public List<PlaceDTO> mainRank(PlaceDTO placeDTO){
//
//        Place place = modelMapper.map(placeDTO, Place.class);
//        return placeRepository.mainPlaceRank(placeDTO);
//    };
}
