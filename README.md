"# image-hoster-project-upgrad" 

In this project, one will learn how to develop REST API endpoints in the following controllers:

1. Signup-controller: In this controller, the user will able to sign up for an account.

2. Authentication-controller: After signing up, the user needs to sign in. This controller authenticates the user based on the credentials provided. After authentication, the user will be given an 'access token', which will be required to perform any further operation.

3. Image-upload-controller: Using the 'access token', the user can upload an image through this controller. But the image is not 'ACTIVE' until reviewed by the admin.

4. Admin-controller: The admin needs to review all the images uploaded by users. This controller provides the admin with the details about all the images. Once the admin has reviewed the image, he can update it (if needed) and make it 'ACTIVE' through this controller.
