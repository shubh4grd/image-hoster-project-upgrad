package com.upgrad.technical.service.business;

import com.upgrad.technical.service.dao.ImageDao;
import com.upgrad.technical.service.entity.ImageEntity;
import com.upgrad.technical.service.entity.UserAuthTokenEntity;
import com.upgrad.technical.service.exception.UploadFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ImageUploadService {

    @Autowired
    private ImageDao imageDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public ImageEntity upload(ImageEntity imageEntity, final String authorizationToken) throws UploadFailedException {
        UserAuthTokenEntity userAuthTokenEntity = imageDao.getUserAuthToken(authorizationToken);
        if (userAuthTokenEntity==null){
            throw new UploadFailedException("UP-001","User is not Signed in, sign in to upload an image");
        }else {
            imageEntity.setUser_id(userAuthTokenEntity.getUser());
            imageDao.createImage(imageEntity);
            return imageEntity;
        }
    }
}
