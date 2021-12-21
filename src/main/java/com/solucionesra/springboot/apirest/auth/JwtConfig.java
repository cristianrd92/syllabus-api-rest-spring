package com.solucionesra.springboot.apirest.auth;

public class JwtConfig {
	
	public static final String LLAVE_SECRETA = "solucionesra@cristian@fabian";
	
	public static final String RSA_PRIVADA = "-----BEGIN RSA PRIVATE KEY-----\r\n"
			+ "MIIEowIBAAKCAQEA2wGNq4A70PPlH3ej3c1I4j+7SCOIs0rHpKjWZHPuLRjUpRzD\r\n"
			+ "/1bpyGyxFlW61x0wyce9hhSeTF/wAKY+xDEtUtSpx0+5GfEDjsSY8d3Ec3kn0btY\r\n"
			+ "oSBRByIKC2f1ziog7bKup16v7HpFNjmrEfuWWPKubR3eqFNtlCxSTGlIaFe/LNlp\r\n"
			+ "ZMHyJ7t9egPKnHBFMRcwq+mN33nN0HU8yt1WMLOk2c9Mk4iJ66SYvOUECJ9H9vKp\r\n"
			+ "pDsAEIvMAjNZW7Y8Scy0NhXYP+VAhFIYmKtuQaKgr5seQvp/GwKh+DD6g6BCp0u2\r\n"
			+ "bjCS34A/OuFhzXSPq4q/ky+5OYX7gVqY9QqJUQIDAQABAoIBAFPkz3FACSntE/su\r\n"
			+ "6H3h0qagxUxIGGieH8Yqyx3l7volhefOEiKxF5MoV9n3c0BONU9GjYpcQZt+MMD+\r\n"
			+ "7hJXyEXvoy3vkzelxLgfBXu5eTTG14MXIkduyrtRxclCxL16E+0aQp9ovTl4SSUR\r\n"
			+ "iD+2QYzHQhwYkh+m/dYB+HC+7/bP2IzpKsTUIY1Jq528mUTNSXNaH6k4nU4S/eIV\r\n"
			+ "Nm80wGuuX0x75NsH2Qt+Fj9krIcKRpqhQb3bfnBnOIjcBbaNk+AcJ/1u0Zy7Q3vj\r\n"
			+ "/F+euC7CvQ/zyty6wOYXVzIRbn7OXnY16Ed0UMrFlYXTAC/FsUS0YPkrO9omSffW\r\n"
			+ "UGXU3AECgYEA+UfAbGwPoBxINg5pldzup6u/iycA++jOQxcgaMbc4N0iBv/s6RmQ\r\n"
			+ "ByUgr90O2d4AXsCVmoSRsauLRiAtVCWcJDVd1ATY5Cd1AXh5SYA6BoGqWbKNBzjY\r\n"
			+ "300lssG86HC7wiOZFuu5MyPyKfCk59r/tx3+IW0AZQToEm0hGIwwlZECgYEA4Oji\r\n"
			+ "NlSioiOlW7f/VNMEnaWcTdHqKm9R61yeIWCXGECf5JHpJVXtQJzbxWn1RFM880xe\r\n"
			+ "1GBpNsdogRGrUiSkMz+OfwR5dorSR5miQFwHZcGsbrrjFyV77IoZskpnIoT4QE6g\r\n"
			+ "vhEeYXcblhi0ByOzJnSMptJFLmaU3+PbRv9918ECgYEA5lxjOw6lZe2c4a9x7VbM\r\n"
			+ "iNATYzF5HFIogiFWBt128qoHrvZEZakEy0WJLluXRMh+CRHpbnCB/vEq9HP5z6At\r\n"
			+ "6dwTSEHuePDplKFTXBQOC0M6PEffQcRLzheJ9eZJf3fmWuLwrxjI/bSDLjS+/7AG\r\n"
			+ "zZ7+4L7NNY1SNOz6P6gVobECgYAD3FuNvTQxflUnVug+J4Urw50ERPc1FUYduTBJ\r\n"
			+ "uGO/mdRk0xBigLAa7NZk9CWur5MbRhNWp/UGaLPli00akuBytMOYcJY5L7beXcfI\r\n"
			+ "PQwvdfykxfaB/jW7YL+CLP7KO+gbCn595MNVvvqtP+1PNhCsN1H2xyNC4aXqlfm8\r\n"
			+ "pUQewQKBgHqkhaDK92Xoiaej+xjWs3jrw5zhZq4aDfh2t5zNFNnmfHOTFIxxmT9x\r\n"
			+ "dq+WEwbZQRJX9+dau8+RRj/fu4S2IxMthHw+D3p4JOLsYUcbmjRWceZs07FreukN\r\n"
			+ "Fm+L62EgoGv4CDxIH6AEf2CAcO2pylTUXkjydU6FnuO/qczAfx0g\r\n"
			+ "-----END RSA PRIVATE KEY-----";

	public static final String RSA_PUBLICA = "-----BEGIN PUBLIC KEY-----\r\n"
			+ "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA2wGNq4A70PPlH3ej3c1I\r\n"
			+ "4j+7SCOIs0rHpKjWZHPuLRjUpRzD/1bpyGyxFlW61x0wyce9hhSeTF/wAKY+xDEt\r\n"
			+ "UtSpx0+5GfEDjsSY8d3Ec3kn0btYoSBRByIKC2f1ziog7bKup16v7HpFNjmrEfuW\r\n"
			+ "WPKubR3eqFNtlCxSTGlIaFe/LNlpZMHyJ7t9egPKnHBFMRcwq+mN33nN0HU8yt1W\r\n"
			+ "MLOk2c9Mk4iJ66SYvOUECJ9H9vKppDsAEIvMAjNZW7Y8Scy0NhXYP+VAhFIYmKtu\r\n"
			+ "QaKgr5seQvp/GwKh+DD6g6BCp0u2bjCS34A/OuFhzXSPq4q/ky+5OYX7gVqY9QqJ\r\n"
			+ "UQIDAQAB\r\n"
			+ "-----END PUBLIC KEY-----";
	
}
