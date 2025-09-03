# 安全配置说明

## 环境变量配置

为了保护敏感信息，本项目使用环境变量来管理配置。请按照以下步骤设置：

### 1. 复制环境变量模板
```bash
cp sky_server/.env.example sky_server/.env
```

### 2. 编辑环境变量文件
在 `sky_server/.env` 文件中填入真实的配置值：

```properties
# 阿里云OSS配置
ALIBABA_CLOUD_ACCESS_KEY_ID=你的阿里云AccessKey ID
ALIBABA_CLOUD_ACCESS_KEY_SECRET=你的阿里云AccessKey Secret

# 微信支付配置  
WECHAT_PAY_MERCHANT_ID=你的微信支付商户号
WECHAT_PAY_MERCHANT_SERIAL_NUMBER=你的微信支付序列号

# 数据库配置
DB_PASSWORD=你的数据库密码
```

### 3. 系统环境变量设置

#### Windows (PowerShell)
```powershell
$env:ALIBABA_CLOUD_ACCESS_KEY_ID="你的AccessKey ID"
$env:ALIBABA_CLOUD_ACCESS_KEY_SECRET="你的AccessKey Secret"
```

#### Linux/macOS
```bash
export ALIBABA_CLOUD_ACCESS_KEY_ID="你的AccessKey ID"
export ALIBABA_CLOUD_ACCESS_KEY_SECRET="你的AccessKey Secret"
```

### 4. IDE配置
在IntelliJ IDEA中：
1. 打开 Run/Debug Configurations
2. 在 Environment variables 中添加相应的环境变量

## 注意事项
- 永远不要将 `.env` 文件提交到版本控制系统
- 定期更换AccessKey和密码
- 使用最小权限原则配置云服务权限