package org.example.pnubookstore.domain.product.service;

import lombok.RequiredArgsConstructor;
import org.example.pnubookstore.core.exception.Exception404;
import org.example.pnubookstore.core.s3.S3Uploader;
import org.example.pnubookstore.domain.product.dto.CreateProductDto;
import org.example.pnubookstore.domain.product.ProductExceptionStatus;
import org.example.pnubookstore.domain.product.dto.FindProductDto;
import org.example.pnubookstore.domain.product.dto.FindProductsDto;
import org.example.pnubookstore.domain.product.dto.UpdateProductDto;
import org.example.pnubookstore.domain.product.entity.Product;
import org.example.pnubookstore.domain.product.entity.ProductPicture;
import org.example.pnubookstore.domain.product.entity.Subject;
import org.example.pnubookstore.domain.product.repository.ProductJpaRepository;
import org.example.pnubookstore.domain.product.repository.ProductPictureJpaRepository;
import org.example.pnubookstore.domain.product.repository.SubjectJpaRepository;
import org.example.pnubookstore.domain.product.repository.UserJpaRepositoryForProduct;
import org.example.pnubookstore.domain.user.entity.User;
import org.hibernate.annotations.processing.Find;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    private final S3Uploader s3Uploader;

    @Transactional
    // 물품 등록
    public void createProduct(CreateProductDto createProductDto, List<MultipartFile> imageFiles) throws IOException {
        // 유저 존재 여부 체크(추후 변경될 수 있음)
        User findedSeller = findUser(createProductDto);

        // 과목 존재 여부 체크
        Subject findedSubject = findSubject(createProductDto);

        // 과목이 존재하지 않을 시 과목 저장)
        if (findedSubject == null){
            findedSubject = saveSubject(createProductDto);
        }

        // 물품 저장
        Product createdProduct = saveProduct(createProductDto, findedSeller, findedSubject);

        // 물품 사진 저장(추후 변경 예정)
        saveImages(imageFiles, createdProduct);
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
    public List<FindProductsDto> findProductList(){
        List<Product> productList = productJpaRepository.findAll();

        List<FindProductsDto> findProductsDtoList = new ArrayList<>();

        for (Product product : productList){
            String pictureUrl = productPictureJpaRepository.findFirstByProduct(product).getUrl();

            findProductsDtoList.add(
                    new FindProductsDto(product.getProductName(), pictureUrl, product.getSeller().getNickname(), product.getPrice(), product.getIsBargain())
            );
        }

        return findProductsDtoList;
    }

    @Transactional
    public void updateProduct(Long productId, CreateProductDto updateProductDto){
        findUser(updateProductDto);

        Product findedProduct = productJpaRepository.findById(productId)
                .orElseThrow(() -> new Exception404(ProductExceptionStatus.PRODUCT_NOT_FOUND.getErrorMessage()));

        Subject findedSubject = findSubject(updateProductDto);

        findedProduct.updateProduct(updateProductDto, findedSubject);

        // 이미지 변경 - 추가 예정(안했었네요...)
    }

    private User findUser(CreateProductDto createProductDto){
        return userJpaRepositoryForProduct.findUserByEmail(createProductDto.getSellerEmail())
                .orElseThrow(() -> new Exception404(ProductExceptionStatus.USER_NOT_FOUND.getErrorMessage()));
    }

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

    private Product saveProduct(CreateProductDto createProductDto, User seller, Subject subject){
        return productJpaRepository.save(
                Product.builder()
                        .seller(seller)
                        .subject(subject)
                        .productName(createProductDto.getProductName())
                        .price(createProductDto.getPrice())
                        .description(createProductDto.getDescription())
                        .author(createProductDto.getAuthor())
                        .pubDate(createProductDto.getPubDate())
                        .isBargain(createProductDto.getIsBargain())
                        .canBargainReason(createProductDto.getCanBargainReason())
                        .saleStatus(createProductDto.getSaleStatus())
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
}
