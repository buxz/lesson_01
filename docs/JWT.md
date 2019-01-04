## 配置JWT
> JWT是一种用户双方之间传递安全信息的简洁的、URL安全的表述性声明规范。
> JWT（Json Web Token）作为一个开放的标准（RFC 7519），
> 定义了一种简洁的、自包含的方法用于通信双方之间以Json对象的形式进行安全性信息传递，
> 传递时有数字签名所以信息时安全的，JWT使用RSA公钥密钥的形式进行签名。
## 项目配置
### 1. 添加依赖
```html
        <!-- JWT 依赖-->
		<dependency>
			<groupId>io.jsonwebtoken</groupId>
			<artifactId>jjwt</artifactId>
			<version>RELEASE</version>
		</dependency>
```