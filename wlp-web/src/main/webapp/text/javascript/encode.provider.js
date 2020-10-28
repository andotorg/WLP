var AuthKeyProvider = {
	encode : function(type, password) {
		if (type == 'SIMPLE') {
			return Base64.encode(password);
		}
		if (type == 'SAFE') {
			return hex_md5(password + "FARM");
		}
		alert("not find the type " + type
				+ " by AuthKeyProvider(encode.provider.js)");
		return password;
	}
};