#!/bin/bash
ssh -p "${SERVER_PORT}" "${SERVER_USERNAME}"@"${SERVER_HOST}" -i key.txt -t -t -o StrictHostKeyChecking=no << 'ENDSSH'
cd ~/debezium
set +a
source .env
start=$(date +"%s")
aws ecr get-login-password --region $AWS_REGION | docker login --username AWS --password-stdin $AWS_ECR_REGISTRY
if [ "$(docker ps -q -f name=$CONTAINER_NAME)" ]; then
    echo "Container is running -> stopping it..."
    docker system prune -af
    docker stop $CONTAINER_NAME;
    docker rm $CONTAINER_NAME
fi
docker run -d --rm -p 9002:9002 --env-file .env --name $CONTAINER_NAME  $AWS_ECR_REGISTRY/$ECR_REPOSITORY:$IMAGE_TAG
exit
ENDSSH

if [ $? -eq 0 ]; then
  exit 0
else
  exit 1
fi

end=$(date +"%s")

diff=$(($end - $start))

echo "Deployed in : ${diff}s"
