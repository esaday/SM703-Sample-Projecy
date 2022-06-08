variable "lambda_payload_filename" {
  default = "../target/SM703-1.0-SNAPSHOT.jar"
}

variable "lambda_function_handler" {
  default = "handler.LambdaHandler"
}

variable "lambda_runtime" {
  default = "java8"
}

variable "api_path" {
  default = "{proxy+}"
}

variable "api_env_stage_name" {
  default = "terraform-lambda-java-stage"
}