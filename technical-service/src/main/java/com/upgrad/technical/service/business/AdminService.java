package com.upgrad.technical.service.business;


import com.upgrad.technical.service.dao.ImageDao;
import com.upgrad.technical.service.entity.ImageEntity;
import com.upgrad.technical.service.entity.UserAuthTokenEntity;
import com.upgrad.technical.service.exception.ImageNotFoundException;
import com.upgrad.technical.service.exception.UnauthorizedException;
import com.upgrad.technical.service.exception.UserNotSignedInException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminService {

    @Autowired
    private ImageDao imageDao;

    public ImageEntity getImage(final String imageUuid, final String authorization) throws ImageNotFoundException, UnauthorizedException, UserNotSignedInException {

        UserAuthTokenEntity userAuthTokenEntity = imageDao.getUserAuthToken(authorization);
        if(userAuthTokenEntity==null){
            throw new UserNotSignedInException("USR-001","You are not Signed in, sign in first to get the details of the image");
        }else {
            String role = userAuthTokenEntity.getUser().getRole();
            if (!role.equals("admin")) {
                throw new UnauthorizedException("ATH-001", "UNAUTHORIZED Access, Entered user is not an admin");
            } else {
                ImageEntity imageEntity = imageDao.getImage(imageUuid);
                if (imageEntity == null) {
                    throw new ImageNotFoundException("IMG-001", "Image with Uuid not found");
                }else {
                    return imageEntity;
                }
            }
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ImageEntity updateImage(final ImageEntity imageEntity, final String authorization) throws ImageNotFoundException, UnauthorizedException, UserNotSignedInException {
        UserAuthTokenEntity userAuthTokenEntity = imageDao.getUserAuthToken(authorization);
        if(userAuthTokenEntity==null){
            throw new UserNotSignedInException("USR-001","You are not Signed in, sign in first to get the details of the image");
        }else {
            String role = userAuthTokenEntity.getUser().getRole();
            if (!role.equals("admin")) {
                throw new UnauthorizedException("ATH-001", "UNAUTHORIZED Access, Entered user is not an admin");
            } else {
                ImageEntity updateImageEntity = imageDao.getImageById(imageEntity.getId());
                if (updateImageEntity == null) {
                    throw new ImageNotFoundException("IMG-001", "Image with Uuid not found");
                }else {
                    updateImageEntity.setImage(imageEntity.getImage());
                    updateImageEntity.setName(imageEntity.getName());
                    updateImageEntity.setDescription(imageEntity.getDescription());
                    updateImageEntity.setStatus(imageEntity.getStatus());
                    imageDao.updateImage(updateImageEntity);
                    return updateImageEntity;
                }
            }
        }

    }
}
