#Sample configuration for intializing backend through cli options
# terraform init \
# -backend-config=bucket=esad-703-tf-state \
# -backend-config=key=environment/{env}.tfstate -backend-config=region=eu-west-1 \
# -backend-config=dynamodb_table=terraform-lock


terraform {
  backend "s3" {}
}

provider "aws" {
  region = "eu-west-1"
}