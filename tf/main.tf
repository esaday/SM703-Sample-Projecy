terraform {
  backend "s3" {
    bucket = "esad-703-tf-state"
    key    = "terraform"
    region = "eu-west-1"
  }
}

provider "aws" {
  region = "eu-west-1"
}