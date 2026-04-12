# email-api

# BUILD PRODUCTION
docker build . -t oryzentsac/apps:email-api-25032026-1000
docker push oryzentsac/apps:email-api-25032026-1000

# RUN PRODUCTION
docker pull oryzentsac/apps:email-api-25032026-1000

docker run \
-e SPRING_PROFILES_ACTIVE=prod \
--env-file /root/creds/email-api/.env \
--name email-api \
-p 127.0.0.1:1002:80 \
--network shared_net \
--restart=unless-stopped \
-d oryzentsac/apps:email-api-25032026-1000