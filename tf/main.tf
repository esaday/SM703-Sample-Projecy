terraform {
  backend "s3" {
  }
}

#It is not possible to assume role with data.aws_iam_role
provider "aws" {
  region = "eu-west-1"
}