# 安全配置说明

## 环境变量配置

为了保护敏感信息，项目使用环境变量来管理配置。请按照以下步骤设置：

### 1. 复制环境变量模板
```bash
cp sky_server/.env.example sky_server/.env
```

### 2. 编辑.env文件
根据您的实际环境修改以下变量：

#### 数据库配置
- `DB_PASSWORD`: MySQL数据库密码

#### Redis配置  
- `REDIS_PASSWORD`: Redis密码

#### JWT密钥配置
- `JWT_ADMIN_SECRET_KEY`: 管理员JWT签名密钥
- `JWT_USER_SECRET_KEY`: 用户JWT签名密钥

#### 商店信息
- `SHOP_ADDRESS`: 商店地址

#### 第三方服务配置
- `BAIDU_MAP_AK`: 百度地图API密钥
- `WECHAT_APPID`: 微信小程序AppID
- `WECHAT_SECRET`: 微信小程序Secret
- `ALIYUN_ACCESS_KEY_ID`: 阿里云AccessKey ID
- `ALIYUN_ACCESS_KEY_SECRET`: 阿里云AccessKey Secret

### 3. 在IDE中配置环境变量
如果使用IntelliJ IDEA，可以在运行配置中添加环境变量，或者安装EnvFile插件来加载.env文件。

### 4. 生产环境配置
在生产环境中，请通过系统环境变量或容器编排工具（如Docker、Kubernetes）来设置这些变量，不要使用.env文件。

## 安全注意事项

1. **永远不要将.env文件提交到版本控制系统**
2. **定期更换密钥和密码**
3. **使用强密码和复杂的JWT密钥**
4. **在生产环境中使用更安全的密钥管理方案**

## 默认值说明

配置文件中的默认值（冒号后的值）仅用于开发环境，生产环境请务必设置环境变量覆盖这些默认值。
# 安全配置说明

## 环境变量配置

为了保护敏感信息，项目使用环境变量来管理配置。请按照以下步骤设置：

### 1. 复制环境变量模板
```bash
cp sky_server/.env.example sky_server/.env
```

### 2. 编辑.env文件
根据您的实际环境修改以下变量：

#### 数据库配置
- `DB_PASSWORD`: MySQL数据库密码

#### Redis配置  
- `REDIS_PASSWORD`: Redis密码

#### JWT密钥配置
- `JWT_ADMIN_SECRET_KEY`: 管理员JWT签名密钥
- `JWT_USER_SECRET_KEY`: 用户JWT签名密钥

#### 商店信息
- `SHOP_ADDRESS`: 商店地址

#### 第三方服务配置
- `BAIDU_MAP_AK`: 百度地图API密钥
- `WECHAT_APPID`: 微信小程序AppID
- `WECHAT_SECRET`: 微信小程序Secret
- `ALIYUN_ACCESS_KEY_ID`: 阿里云AccessKey ID
- `ALIYUN_ACCESS_KEY_SECRET`: 阿里云AccessKey Secret

### 3. 在IDE中配置环境变量
如果使用IntelliJ IDEA，可以在运行配置中添加环境变量，或者安装EnvFile插件来加载.env文件。

### 4. 生产环境配置
在生产环境中，请通过系统环境变量或容器编排工具（如Docker、Kubernetes）来设置这些变量，不要使用.env文件。

## 安全注意事项

1. **永远不要将.env文件提交到版本控制系统**
2. **定期更换密钥和密码**
3. **使用强密码和复杂的JWT密钥**
4. **在生产环境中使用更安全的密钥管理方案**

## 默认值说明

配置文件中的默认值（冒号后的值）仅用于开发环境，生产环境请务必设置环境变量覆盖这些默认值。
