package com.cod.mymarket.review.service;

import com.cod.mymarket.review.entity.Review;
import com.cod.mymarket.review.repository.ReviewRepository;
import com.cod.mymarket.product.entity.Product;
import com.cod.mymarket.product.service.ProductService;
import com.cod.mymarket.member.entity.Member;
import com.cod.mymarket.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private MemberService memberService;

    @Value("${custom.genFileDirPath}")
    private String genFileDirPath;

    public Review createReview(Long productId, String content, String title, int score, List<MultipartFile> reviewImgs, String username) {
        Product product = productService.getProduct(productId);
        Member member = memberService.findByUsername(username);

        // 이미지 저장 및 경로 설정
        List<String> reviewImages = new ArrayList<>();
        for (MultipartFile reviewImg : reviewImgs) {
            String reviewImgRelPath = "reviews/" + UUID.randomUUID().toString() + ".jpg";
            File reviewImgFile = new File(genFileDirPath + "/" + reviewImgRelPath);
            reviewImgFile.getParentFile().mkdirs();

            try {
                reviewImg.transferTo(reviewImgFile);
                reviewImages.add(reviewImgRelPath);
            } catch (IOException e) {
                e.printStackTrace(); // 적절한 예외 처리
            }
        }

        // 리뷰 객체 생성 및 설정
        Review review = Review.builder()
                .content(content)
                .title(title)
                .score(score)
                .member(member)
                .product(product)
                .createdDate(LocalDateTime.now())
                .reviewImages(reviewImages)
                .build();



        review.setReviewImages(reviewImages);

        // 리뷰 저장
        return reviewRepository.save(review);
    }
}