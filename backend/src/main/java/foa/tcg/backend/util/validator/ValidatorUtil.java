package foa.tcg.backend.util.validator;

import foa.tcg.backend.exception.UserBadRequestException;
import foa.tcg.backend.model.dto.UserDto;
import foa.tcg.backend.model.dto.ProductDto;
import foa.tcg.backend.model.entity.User;
import foa.tcg.backend.model.entity.Product;

public class ValidatorUtil {

    final static String EMAILREGEX = "^[a-zA-Z0-9_+-]+(.[a-zA-Z0-9_+-]+)*@([a-zA-Z0-9][a-zA-Z0-9-]*[a-zA-Z0-9]*\\.)+[a-zA-Z]{2,}$";

    public static void validateUserDto(UserDto userDto) throws UserBadRequestException {
        if (userDto.getId() == null) {
            throw new UserBadRequestException("Id is required.");
        }
        if (userDto.getName().isBlank()) {
            throw new UserBadRequestException("Name is required.");
        }
        if (userDto.getName().length() > 250) {
            throw new UserBadRequestException("Name must be between 1 and 250 characters.");
        }
        if (userDto.getEmail().isBlank()) {
            throw new UserBadRequestException("Email address is required.");
        }
        if (!userDto.getEmail().matches(EMAILREGEX)) {
            throw new UserBadRequestException("Email address seems to be invalid.");
        }
    }

    public static void validateUser(User user) throws UserBadRequestException {
        if (user.id() == null) {
            throw new UserBadRequestException("id is required.");
        }
        if (user.name().isBlank()) {
            throw new UserBadRequestException("Name is required.");
        }
        if (user.name().length() > 250) {
            throw new UserBadRequestException("Name must be between 1 and 250 characters.");
        }
        if (user.email().isBlank()) {
            throw new UserBadRequestException("Email address is required.");
        }
        if (!user.email().matches(EMAILREGEX)) {
            throw new UserBadRequestException("Email address seems to be invalid.");
        }
    }

    public static void validateProductDto(ProductDto productDto) throws UserBadRequestException {
        if (productDto.getId() == null) {
            throw new UserBadRequestException("Id is required.");
        }
        if (productDto.getName().isBlank()) {
            throw new UserBadRequestException("Name is required.");
        }
        if (productDto.getPrice() == null || productDto.getPrice() < 0) {
            throw new UserBadRequestException("Price must be positive.");
        }
    }

    public static void validateProduct(Product product) throws UserBadRequestException {
        if (product.id() == null) {
            throw new UserBadRequestException("Id is required.");
        }
        if (product.name().isBlank()) {
            throw new UserBadRequestException("Name is required.");
        }
        if (product.price() == null || product.price() < 0) {
            throw new UserBadRequestException("Price must be positive.");
        }
    }

}
