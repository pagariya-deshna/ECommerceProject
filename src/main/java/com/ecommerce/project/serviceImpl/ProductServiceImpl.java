package com.ecommerce.project.serviceImpl;

import java.io.File;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.project.model.AddToCart;
import com.ecommerce.project.model.Brand;
import com.ecommerce.project.model.Customer;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.model.ProductImage;
import com.ecommerce.project.repository.BrandRepository;
import com.ecommerce.project.repository.ProductImageRepository;
import com.ecommerce.project.repository.ProductRepository;
import com.ecommerce.project.request.BrandRequest;
import com.ecommerce.project.request.ProductRequest;
import com.ecommerce.project.response.RegistrationResponse;
import com.ecommerce.project.service.ProductService;
import com.ecommerce.project.util.IConstants;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private BrandRepository brandRepository;

	@Autowired
	private ProductImageRepository productImageRepository;

	private final String FOLDER_PATH = "D:\\Project workspace\\ProductImage\\";

	@Override
	public RegistrationResponse addProduct(ProductRequest productRequest) {
		RegistrationResponse registrationResponse = new RegistrationResponse();
		if (productRequest != null) {
//			Product existingProduct = productRepository.findByName(productRequest.getName());
			Product product = new Product();
			product.setProductCode(UUID.randomUUID().toString());
			product.setDate(LocalDateTime.now());
			product.setName(productRequest.getName());
			product.setQuantity(productRequest.getQuantity());
			product.setPrice(productRequest.getPricePerProduct());
			Brand brand = new Brand();
			BrandRequest brandRequest = productRequest.getBrand();
			if (brandRequest.getBrandName() != null && !(brandRequest.getBrandName().equalsIgnoreCase(""))) {
				Brand oldBrand = null;
				oldBrand = brandRepository.findByBrandName(brandRequest.getBrandName());
				if (oldBrand != null ) {
					product.setBrands(oldBrand);
					Product save = productRepository.save(product);
					registrationResponse.setMessage(IConstants.DATA_ADDED);
					registrationResponse.setStatus(IConstants.OK);
					registrationResponse.setObject(save);
				} else {
					brand.setBrandName(brandRequest.getBrandName());
					product.setBrands(brand);
					Product save = productRepository.save(product);
					registrationResponse.setMessage(IConstants.DATA_ADDED);
					registrationResponse.setStatus(IConstants.OK);
					registrationResponse.setObject(save);
				}
			} else {
				registrationResponse.setMessage(IConstants.DATA_NOT_ADDED);
				registrationResponse.setStatus(IConstants.BAD_REQUEST);
				registrationResponse.setObject("");
			}
		}
		return registrationResponse;
	}


	@Override
	public RegistrationResponse updateProduct(Long id, ProductRequest productRequest) {
		RegistrationResponse registrationResponse = new RegistrationResponse();
		if (!id.equals("") && productRequest != null) {
			Product oldProduct = productRepository.findById(id).orElse(null);
			if (oldProduct != null) {
				oldProduct.setDate(LocalDateTime.now());
				oldProduct.setName(productRequest.getName());
				oldProduct.setQuantity(productRequest.getQuantity());
				oldProduct.setPrice(productRequest.getPricePerProduct());
				Brand brand = new Brand();
				BrandRequest brandRequest = productRequest.getBrand();
				if (brandRequest.getBrandName() != null && !(brandRequest.getBrandName().equalsIgnoreCase(""))) {
					Brand oldBrand = null;
					oldBrand = brandRepository.findByBrandName(brandRequest.getBrandName());
					if (oldBrand != null) {
						oldProduct.setBrands(oldBrand);
						Product save = productRepository.save(oldProduct);
						registrationResponse.setMessage(IConstants.DATA_UPDATED);
						registrationResponse.setStatus(IConstants.OK);
						registrationResponse.setObject(save);
					} else {
						brand.setBrandName(brandRequest.getBrandName());
						oldProduct.setBrands(brand);
						Product save = productRepository.save(oldProduct);
						registrationResponse.setMessage(IConstants.DATA_UPDATED);
						registrationResponse.setStatus(IConstants.OK);
						registrationResponse.setObject(save);
					}
				} else {
					registrationResponse.setMessage(IConstants.ID_NULL);
					registrationResponse.setStatus(IConstants.BAD_REQUEST);
					registrationResponse.setObject("");
				}
			}
		}
		return registrationResponse;
	}

	@Override
	public RegistrationResponse deleteProduct(Long id) {
		RegistrationResponse registrationResponse = new RegistrationResponse();
		Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
		Brand brands = product.getBrands();
		if (product != null) {

			// Remove the product from the brand's products list
			brands.getProducts().remove(product);
			
			productRepository.deleteById(id);
			registrationResponse.setMessage(IConstants.PRODUCT_DELETED);
			registrationResponse.setStatus(IConstants.OK);
			registrationResponse.setObject("");
		} else {
			registrationResponse.setMessage(IConstants.PRODUCT_NOT_FOUND + " with id : " + id);
			registrationResponse.setStatus(IConstants.BAD_REQUEST);
			registrationResponse.setObject("");
		}
		return registrationResponse;
	}

	@Override
	public RegistrationResponse getAllProducts() {
		RegistrationResponse registrationResponse = new RegistrationResponse();
		try {
			List<Product> productList = productRepository.findAll();
			if (productList != null && !productList.isEmpty()) {
				registrationResponse.setMessage(IConstants.DATA_FOUND);
				registrationResponse.setStatus(IConstants.OK);
				registrationResponse.setObject(productList);
			} else {
				registrationResponse.setMessage(IConstants.DATA_NOT_FOUND);
				registrationResponse.setStatus(IConstants.BAD_REQUEST);
				registrationResponse.setObject(Collections.emptyList());
			}
		} catch (Exception e) {
			registrationResponse.setMessage(IConstants.ERROR_OCCURRED);
			registrationResponse.setStatus(IConstants.INTERNAL_SERVER_ERROR);
			registrationResponse.setObject(Collections.emptyList());
		}

		return registrationResponse;
	}

//	@Override
//	public RegistrationResponse getProductsByBrand(String brandName) {
//		RegistrationResponse registrationResponse = new RegistrationResponse();
//		Brand oldBrand=brandRepository.findByBrandName(brandName);
//		if(oldBrand==null) {
//			registrationResponse.setMessage(IConstants.DATA_NOT_FOUND);
//			registrationResponse.setStatus(IConstants.BAD_REQUEST);
//			registrationResponse.setObject(Collections.emptyList());
//		}
//	    List<Product> products = productRepository.findByBrandName(brandName);
//	    if (!products.isEmpty()) {
//	        registrationResponse.setMessage(IConstants.DATA_FOUND);
//	        registrationResponse.setStatus(IConstants.OK);
//	        registrationResponse.setObject(products);
//	    } else {
//			registrationResponse.setMessage(IConstants.DATA_NOT_FOUND);
//	        registrationResponse.setStatus(IConstants.BAD_REQUEST);
//			registrationResponse.setObject(Collections.emptyList());
//	    }
//		
//		return registrationResponse;
//	}

	@Override
	public RegistrationResponse addBrand(BrandRequest brandRequest) {
		RegistrationResponse registrationResponse = new RegistrationResponse();
		if (brandRequest != null && !(brandRequest.getBrandName().equalsIgnoreCase(""))) {
			Boolean oldBrand = null;
			oldBrand = brandRepository.existsByBrandName(brandRequest.getBrandName());
			if (oldBrand) {
				registrationResponse.setMessage(IConstants.BRAND_EXISTS + "Please enter some other brand");
				registrationResponse.setStatus(IConstants.BAD_REQUEST);
				registrationResponse.setObject("");
			} else {
				Brand brand = new Brand();
				brand.setBrandName(brandRequest.getBrandName());
				Brand save = brandRepository.save(brand);
				registrationResponse.setMessage(IConstants.DATA_ADDED);
				registrationResponse.setStatus(IConstants.OK);
				registrationResponse.setObject(save);
			}
		} else {
			registrationResponse.setMessage(IConstants.DATA_NOT_ADDED);
			registrationResponse.setStatus(IConstants.OK);
			registrationResponse.setObject("");
		}
		return registrationResponse;
	}

//	following code is for adding product image but removing it as mapping not able to done...
	@Override
	public RegistrationResponse addProductImage(MultipartFile image) {
		RegistrationResponse registrationResponse = new RegistrationResponse();

		String filePath = FOLDER_PATH + image.getOriginalFilename();
		ProductImage productImage = productImageRepository.save(ProductImage.builder().name(image.getOriginalFilename())
				.type(image.getContentType()).filePath(filePath).build());

		try {
			image.transferTo(new File(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (productImage != null) {
			registrationResponse.setMessage(IConstants.IMAGE_UPLOADED + ":" + filePath);
			registrationResponse.setStatus(IConstants.BAD_REQUEST);
			registrationResponse.setObject("");
		}
		return registrationResponse;
	}
}
