class UrlMappings {


	static mappings = {
        "/$lang/$controller/$action?/$id?(.${format})?"{
            constraints {
                // apply constraints here
            }
        }
        "/$controller/$action?/$id?"{
            constraints {
            }
        }
        "/"(view:"/index")
        "500"(view:'/error')
	}
}
