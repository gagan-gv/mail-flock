# Mail Flock

## Overview

Cold mailing has emerged as a vital component in various industries, such as marketing and talent acquisition platforms. However, this approach is not without its challenges, particularly in terms of server efficiency during runtime. Many companies rely on costly services like SendGrid to manage their cold mailing campaigns, leading to increased expenses. Mail Flock presents a cutting-edge solution by acting as a middleman, reducing costs, and optimizing server performance to streamline the time-consuming cold mailing process.

## Design Documentation

- [High Level Design](https://www.notion.so/gaganchordia/Mail-Flock-70d9b073f9c944899540b49d2af47afc?pvs=4)

## API Endpoints

### Authourization

| Endpoint | Description | Mapping |
| :---: | --- | --- |
| /api/auth/register | Registers a user and saves required data in the database and initiates OTP verification | POST |
| /api/auth/verify | Verifies the OTP that has been shared to the user via email | PATCH |
| /api/auth/login | Authorizes the user and returns access and refresh tokens | POST |

### User

| Endpoint | Description | Mapping |
| --- | --- | --- |
| /api/user/ | Takes in updatable parameters and updates the details in the database | PUT |
| /api/user | Deletes the user | DELETE |

### Mailing

| Endpoint | Description | Mapping |
| --- | --- | --- |
| /api/mail/send | Takes in mail content like to mail, bcc, cc, content, subject and text type (boolean \| plain or HTML) and sends mail to all the addresses | POST |
