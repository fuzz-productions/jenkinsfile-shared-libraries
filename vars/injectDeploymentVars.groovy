def call(String baseURL) {
	env.BASE_URL = baseURL
	env.RAW_ENV = env.BRANCH_NAME
	endpointPrefix = env.BRANCH_NAME

	namedBranches = ["production", "sandbox", "staging", "dev"]
	if (!namedBranches.contains(endpointPrefix)) {
  		target = "dev"
  		if (github.isPR()) {
  			target = env.CHANGE_TARGET
  			if (!namedBranches.contains(target)) {
  				target = "dev"
  			}
  		}
		env.CLEAN_ENV = target
  		env.STANDARD_ENV = false
  		env.ENV_TYPE = "branch"
	} else {
		env.STANDARD_ENV = true
		env.ENV_TYPE = endpointPrefix
	}
	if (endpointPrefix == "production") {
		endpointPrefix = ""
		env.IS_PRODUCTION = true
	} else {
		endpointPrefix = endpointPrefix + "."
		env.IS_PRODUCTION = false
	}
	env.DEPLOY_URL="${endpointPrefix}${env.BASE_URL}"
}