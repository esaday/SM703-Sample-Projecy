# defining our lambda and referencing to our java handler function
resource "aws_lambda_function" "java_lambda_function" {
  runtime       = var.lambda_runtime
  function_name = "java_lambda_function_${terraform.workspace}"
  s3_bucket = var.s3_artifact_bucket
  s3_key = var.s3_artifact_key
  handler     = "tr.edu.metu.sm703.SM703Example" #package-name.class-name
  timeout     = 60
  memory_size = 256
  # role for lambda is defined in aws-iam_role resource
  role             = aws_iam_role.iam_role_for_lambda.arn
  source_code_hash = null_resource.build.triggers.main
  depends_on = [
    aws_cloudwatch_log_group.log_group,
    null_resource.build
  ]

}
# defining permission for our lambda, we have allowed API gateway to
# to invoke our lambda handler function
resource "aws_lambda_permission" "java_lambda_function" {
  statement_id  = "AllowAPIGatewayInvoke"
  action        = "lambda:InvokeFunction"
  function_name = aws_lambda_function.java_lambda_function.function_name
  principal     = "apigateway.amazonaws.com"
  # The /*/* portion grants access from any method on any resource
  # within the API Gateway "REST API".
  # source_arn = "${replace(aws_api_gateway_deployment.java_lambda_deploy.execution_arn, var.api_env_stage_name, "")}*/*"
  source_arn = "${aws_api_gateway_deployment.java_lambda_deploy.execution_arn}/*/*"
}



resource "null_resource" "build" {

  provisioner "local-exec" {
    command = "mvn package -f ../pom.xml"
  }

  triggers = {
    main = base64sha256(file("../src/main/java/tr/edu/metu/sm703/SM703Example.java"))
  }

}

// Create a log group for the lambda
resource "aws_cloudwatch_log_group" "log_group" {
  name = "/aws/lambda/java_lambda_function_${terraform.workspace}"
}

# allow lambda to log to cloudwatch
data "aws_iam_policy_document" "cloudwatch_log_group_access_document" {
  statement {
    actions = [
      "logs:CreateLogGroup",
      "logs:CreateLogStream",
      "logs:PutLogEvents"
    ]

    resources = [
      "arn:aws:logs:::*",
    ]
  }
}