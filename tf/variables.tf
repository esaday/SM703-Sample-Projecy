variable "LAMBDA_JAR_LOCATION" {
  default = "./target/SM703-1.0-SNAPSHOT.jar"
}

variable "lambda_function_handler" {
  default = "handler.LambdaHandler"
}

variable "lambda_runtime" {
  default = "java11"
}

variable "api_path" {
  default = "{proxy+}"
}

variable "s3_artifact_bucket" {
  default = "esad-703-jars"
}
#this key should never exist
variable "s3_artifact_key" {
  default = "deployments/SM703-1.0-SNAPSHOT.jar"
}