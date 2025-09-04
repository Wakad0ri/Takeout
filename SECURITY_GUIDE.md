# 项目安全配置指南

## 🚨 重要安全提醒

**如果您的阿里云密钥已经暴露在 Git 历史中，请立即执行以下操作：**

1. **立即更换阿里云 AccessKey**
   - 登录 [阿里云控制台](https://ram.console.aliyun.com/manage/ak)
   - 删除已暴露的 AccessKey: `LTAI5t5n9iYN1FT85PBbp6jU`
   - 创建新的 AccessKey 并更新到本地配置文件中

2. **检查其他云服务密钥**
   - 微信支付密钥
   - 百度地图 AK
   - JWT 密钥

## 📁 配置文件结构

```
sky_server/src/main/resources/
├── application.yml                    # 主配置文件
├── application-dev.yml               # 开发环境配置（安全版本，使用占位符）
├── application-dev-local.yml         # 本地敏感配置（不提交到 Git）
└── application-dev-template.yml      # 配置模板文件
```

## 🔧 快速开始

### 1. 首次设置

```bash
# 1. 复制模板文件
cp sky_server/src/main/resources/application-dev-template.yml sky_server/src/main/resources/application-dev-local.yml

# 2. 编辑本地配置文件，填入真实的密钥和密码
# 注意：application-dev-local.yml 不会被提交到 Git
```

### 2. 配置真实信息

编辑 `application-dev-local.yml` 文件，将所有 `your_xxx_here` 替换为真实的配置值：

```yaml
# 数据库配置
spring:
  datasource:
    password: 你的数据库密码

# 阿里云OSS配置
aliyun:
  oss:
    access-key-id: 你的新AccessKeyId
    access-key-secret: 你的新AccessKeySecret
```

## 🔒 安全最佳实践

### 1. 密钥管理
- ✅ 使用强密码和复杂密钥
- ✅ 定期轮换密钥
- ✅ 不同环境使用不同密钥
- ❌ 永远不要将密钥提交到版本控制系统

### 2. 环境变量（推荐用于生产环境）

```bash
# 设置环境变量
export ALIYUN_ACCESS_KEY_ID="your_access_key_id"
export ALIYUN_ACCESS_KEY_SECRET="your_access_key_secret"
export DB_PASSWORD="your_db_password"
```

### 3. 生产环境配置

创建 `application-prod-local.yml` 用于生产环境：

```yaml
# 生产环境使用环境变量
spring:
  datasource:
    password: ${DB_PASSWORD}

aliyun:
  oss:
    access-key-id: ${ALIYUN_ACCESS_KEY_ID}
    access-key-secret: ${ALIYUN_ACCESS_KEY_SECRET}
```

## 🚫 Git 历史清理

如果敏感信息已经提交到 Git，需要清理历史：

```bash
# ⚠️ 危险操作：会重写 Git 历史
# 建议在执行前备份代码

# 1. 安装 git-filter-repo
pip install git-filter-repo

# 2. 清理包含敏感信息的文件
git filter-repo --path sky_server/src/main/resources/application-dev.yml --invert-paths

# 3. 强制推送（需要团队协调）
git push origin --force --all
```

## 📋 检查清单

在提交代码前，请确认：

- [ ] 没有硬编码的密码、密钥或敏感信息
- [ ] `.gitignore` 文件包含了所有敏感配置文件
- [ ] 使用了环境变量或本地配置文件
- [ ] 更新了所有已暴露的密钥
- [ ] 团队成员了解新的配置方式

## 🆘 紧急情况处理

如果发现密钥泄露：

1. **立即更换所有相关密钥**
2. **检查云服务访问日志**
3. **通知团队成员**
4. **更新所有环境的配置**
5. **清理 Git 历史（如需要）**

## 📞 联系方式

如有安全相关问题，请联系项目负责人。

---

**记住：安全是每个人的责任！** 🔐