#!/bin/bash
ssh -p "${SERVER_PORT}" "${SERVER_USERNAME}"@"${SERVER_HOST}" -i key.txt -t -t -o StrictHostKeyChecking=no << 'ENDSSH'
cd ~/debezium
set +a
source .env
start=$(date +"%s")
docker pull hendisantika/$CONTAINER_NAME
if [ "$(docker ps -q -f name=$CONTAINER_NAME)" ]; then
    echo "Container is running -> stopping it..."
    docker system prune -af
    docker stop $CONTAINER_NAME;
    docker rm $CONTAINER_NAME
fi
docker run -d --rm -p $APP_SERVER_PORT:$APP_SERVER_PORT --env-file .env --name $CONTAINER_NAME  hendisantika/$CONTAINER_NAME:$IMAGE_TAG
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
