# Python image to use
FROM python:3.9-slim


ENV PYTHONUNBUFFERED True

# Copy local code to container image
ENV APP_HOME /app
WORKDIR $APP_HOMEs
COPY . ./

# Install all required packages specified in requirements.txt
RUN pip install -r requirements.txt

# Run web service on container using gunicorn
CMD exec gunicorn --bind :$PORT --workers 1 --threads 8 --timeout 0 main:app