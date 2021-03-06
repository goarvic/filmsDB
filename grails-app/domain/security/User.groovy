package security

class User {

	transient springSecurityService

	String completeName
	String username
	String password
	String email
	boolean enabled = true
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired
	Date dateCreated

	static transients = ['springSecurityService']

	static constraints = {
		email nullable: true, unique: true
		username blank: false, unique: true
		password blank: false
		completeName nullable : true
	}

	static mapping = {
		password column: '`password`'
	}

	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this).collect { it.role }
	}

	def beforeInsert() {
		encodePassword()
		dateCreated = new Date()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService?.passwordEncoder ? springSecurityService.encodePassword(password) : password
	}
}
