def call(String command) {
    sh "#!/bin/bash\n source ~/.bash_profile \n  ${command}"
}

def quiet(String command) {
	try {
    	sh "#!/bin/bash\n source ~/.bash_profile \n  ${command}"
	} catch(Throwable t) {
		
	}
}