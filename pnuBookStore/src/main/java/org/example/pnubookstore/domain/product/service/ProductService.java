package org.example.pnubookstore.domain.product.service;

import lombok.RequiredArgsConstructor;
import org.example.pnubookstore.core.exception.Exception404;
import org.example.pnubookstore.core.s3.S3Uploader;
import org.example.pnubookstore.domain.product.dto.CreateProductDto;
import org.example.pnubookstore.domain.product.ProductExceptionStatus;
import org.example.pnubookstore.domain.product.dto.FindProductDto;
import org.example.pnubookstore.domain.product.dto.FindProductsDto;
import org.example.pnubookstore.domain.product.dto.UpdateProductDto;
import org.example.pnubookstore.domain.product.entity.Location;
import org.example.pnubookstore.domain.product.entity.Product;
import org.example.pnubookstore.domain.product.entity.ProductPicture;
import org.example.pnubookstore.domain.product.entity.Subject;
import org.example.pnubookstore.domain.product.entity.constant.SaleStatus;
import org.example.pnubookstore.domain.product.repository.*;
import org.example.pnubookstore.domain.user.entity.User;
import org.hibernate.annotations.processing.Find;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {

    private final ProductJpaRepository productJpaRepository;
    private final UserJpaRepositoryForProduct userJpaRepositoryForProduct;
    private final SubjectJpaRepository subjectJpaRepository;
    private final ProductPictureJpaRepository productPictureJpaRepository;
    private final LocationJpaRepository locationJpaRepository;
    private final S3Uploader s3Uploader;
    private final PasswordEncoder passwordEncoder;
    private final SubjectCustomRepositoryImpl subjectCustomRepository;

    @Transactional
    // 물품 등록
    public void createProduct(CreateProductDto createProductDto) throws IOException {
        // 유저 존재 여부 체크(추후 변경될 수 있음)
        User findedSeller = userJpaRepositoryForProduct.findById(1L).orElseThrow();
//        User findedSeller = findUser(createProductDto);

        // 과목 존재 여부 체크
        Subject findedSubject = findSubject(createProductDto);

        // 과목이 존재하지 않을 시 과목 저장)
        if (findedSubject == null){
            findedSubject = saveSubject(createProductDto);
        }

        Location createdLocation = saveLocation(createProductDto);

        // 물품 저장
        Product createdProduct = saveProduct(createProductDto, findedSeller, findedSubject, createdLocation);
        createdLocation.setProduct(createdProduct);

        // 물품 사진 저장(추후 변경 예정)
//        saveImages(createProductDto.getProductPictureList(), createdProduct);
    }

    // 물품 조회
    public FindProductDto findProduct(Long productId){
        Product findedProduct = productJpaRepository.findByIdFetchJoin(productId)
                .orElseThrow(() -> new Exception404(ProductExceptionStatus.PRODUCT_NOT_FOUND.getErrorMessage()));

        List<String> productPictureUrlList = productPictureJpaRepository.findAllByProduct(findedProduct)
                .orElseThrow(() -> new Exception404(ProductExceptionStatus.PRODUCT_PICTURES_NOT_FOUND.getErrorMessage()))
                .stream()
                .map(ProductPicture::getUrl)
                .toList();

        return FindProductDto.of(findedProduct, productPictureUrlList);
    }

    // 물품 리스트 조회
    // 물품명, 가격, 저자, 물품 이미지
    public Page<FindProductsDto> findProductList(int page, String college, String department, String professor, String subjectName){
        Pageable pageable = PageRequest.of(page, 10);
        Page<Product> productList = null;

        if(college == "" && department == "" && professor == "" && subjectName == ""){
            productList = productJpaRepository.findAll(pageable);
        }
        else{
            List<Subject> subjects = subjectCustomRepository.findSubjects(
                    college, department, professor, subjectName);
            productList = productJpaRepository.findBySubjectIn(pageable, subjects);
        }

        List<FindProductsDto> findProductsDtoList = new ArrayList<>();

        for (Product product : productList){
//            ProductPicture productPicture = productPictureJpaRepository.findFirstByProduct(product)
//                    .orElseThrow(() -> new Exception404(ProductExceptionStatus.PRODUCT_PICTURES_NOT_FOUND.getErrorMessage()));
//
//            String pictureUrl = productPicture.getUrl();

            findProductsDtoList.add(
                    FindProductsDto.builder()
                            .productId(product.getId())
                            .productName(product.getProductName())
                            .price(product.getPrice())
                            .seller(product.getSeller().getNickname())
                            .build()
            );
        }

        return new PageImpl<>(findProductsDtoList, pageable, productList.getTotalElements());
    }

    @Transactional
    public void updateProduct(Long productId, CreateProductDto updateProductDto) throws IOException {
//        findUser(updateProductDto);

        Product findedProduct = productJpaRepository.findById(productId)
                .orElseThrow(() -> new Exception404(ProductExceptionStatus.PRODUCT_NOT_FOUND.getErrorMessage()));

        Subject findedSubject = findSubject(updateProductDto);

        Location findedLocation = findedProduct.getLocation();
        findedLocation.updateLocation(updateProductDto);


        findedProduct.updateProduct(updateProductDto, findedSubject);

        // 이미지 변경
//        productPictureJpaRepository.deleteAllByProduct(findedProduct);
//        saveImages(updateProductDto.getProductPictureList(), findedProduct);
    }

    @Transactional
    public void deleteProduct(Long productId){
        Product findedProduct = productJpaRepository.findById(productId)
                        .orElseThrow(() -> new Exception404(ProductExceptionStatus.PRODUCT_NOT_FOUND.getErrorMessage()));
        productPictureJpaRepository.deleteAllByProduct(findedProduct);
        productJpaRepository.delete(findedProduct);
    }

//    private User findUser(CreateProductDto createProductDto){
//        return userJpaRepositoryForProduct.findUserByEmail(createProductDto.getSellerEmail())
//                .orElseThrow(() -> new Exception404(ProductExceptionStatus.USER_NOT_FOUND.getErrorMessage()));
//    }

    private Subject findSubject(CreateProductDto createProductDto){
        return subjectJpaRepository.findBySubjectNameAndCollegeAndDepartmentAndProfessor(
                createProductDto.getSubjectName(), createProductDto.getCollege(), createProductDto.getDepartment(), createProductDto.getProfessor());
    }

    private Subject saveSubject(CreateProductDto createProductDto){
        return subjectJpaRepository.save(
                Subject.builder()
                        .subjectName(createProductDto.getSubjectName())
                        .professor(createProductDto.getProfessor())
                        .department(createProductDto.getDepartment())
                        .build());
    }

    private Product saveProduct(CreateProductDto createProductDto, User seller, Subject subject, Location location){
        return productJpaRepository.save(
                Product.builder()
                        .seller(seller)
                        .subject(subject)
                        .location(location)
                        .productName(createProductDto.getProductName())
                        .price(createProductDto.getPrice())
                        .description(createProductDto.getDescription())
                        .author(createProductDto.getAuthor())
                        .pubDate(LocalDateTime.now())
                        .saleStatus(SaleStatus.NOT_YET)
                        .underline(createProductDto.getUnderline())
                        .note(createProductDto.getNote())
                        .naming(createProductDto.getNaming())
                        .discolor(createProductDto.getDiscolor())
                        .damage(createProductDto.getDamage())
                        .build());
    }

    private void saveImages(List<MultipartFile> imageFiles, Product product) throws IOException {
        for(MultipartFile imageFile : imageFiles){
            String imageUrl = s3Uploader.uploadFile(imageFile);
            productPictureJpaRepository.save(
                    ProductPicture.builder()
                            .url(imageUrl)
                            .product(product)
                            .build());
        }
    }

    private Location saveLocation(CreateProductDto createProductDto){
        return locationJpaRepository.save(
                Location.builder()
                        .buildingName(createProductDto.getBuildingName())
                        .lockerNumber(createProductDto.getLockerNumber())
                        .password(passwordEncoder.encode(createProductDto.getPassword()))
                        .build()
        );
    }
}
