build:
	docker build -f Dockerfile.yml -t stocky .

run:
	docker run -p 8080:8080 stocky
