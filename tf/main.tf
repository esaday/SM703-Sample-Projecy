terraform {
  backend "s3" {
    bucket = "esad-703-tf-state"
    key    = "terraform"
    region = "eu-west-1"
  }
}

#It is not possible to assume role with data.aws_iam_role
provider "aws" {
  region = "eu-west-1"
}