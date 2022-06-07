terraform {
  backend "s3" {
    bucket = "esad-703-tf-state"
    key    = "terraform"
    region = "eu-west-1"
  }
}

#It is not possible to assume role with data.aws_iam_role
provider "aws" {
  region     = "eu-west-1"
  access_key = var.aws_access_key
  secret_key = var.aws_secret_key
}