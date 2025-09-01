# 购物车减少数量功能 API 测试示例

## 功能说明
购物车减少数量功能允许用户减少购物车中商品的数量：
- 如果商品数量大于1，则数量减1
- 如果商品数量等于1，则删除该购物车项
- 支持菜品和套餐
- 支持口味区分

## API 接口信息
- **URL**: `PUT /user/shoppingCart/sub`
- **Content-Type**: `application/json`
- **需要认证**: 是（需要用户登录）

## 请求参数示例

### 1. 减少菜品数量（无口味）
```json
{
    "dishId": 1
}
```

### 2. 减少菜品数量（有口味）
```json
{
    "dishId": 1,
    "dishFlavor": "[{\"name\":\"辣度\",\"value\":\"微辣\"},{\"name\":\"温度\",\"value\":\"常温\"}]"
}
```

### 3. 减少套餐数量
```json
{
    "setmealId": 1
}
```

## 响应示例

### 成功响应
```json
{
    "code": 1,
    "msg": null,
    "data": [
        {
            "id": 1,
            "name": "宫保鸡丁",
            "userId": 1,
            "dishId": 1,
            "setmealId": null,
            "dishFlavor": "[{\"name\":\"辣度\",\"value\":\"微辣\"}]",
            "number": 2,
            "amount": 25.00,
            "image": "dish1.jpg",
            "createTime": "2024-08-24T10:30:00"
        }
    ]
}
```

## 使用 curl 测试

### 减少菜品数量
```bash
curl -X PUT http://localhost:8080/user/shoppingCart/sub \
  -H "Content-Type: application/json" \
  -H "token: your_jwt_token_here" \
  -d '{
    "dishId": 1,
    "dishFlavor": "[{\"name\":\"辣度\",\"value\":\"微辣\"}]"
  }'
```

### 减少套餐数量
```bash
curl -X PUT http://localhost:8080/user/shoppingCart/sub \
  -H "Content-Type: application/json" \
  -H "token: your_jwt_token_here" \
  -d '{
    "setmealId": 1
  }'
```

## 使用 Postman 测试

1. **设置请求方法**: PUT
2. **设置URL**: `http://localhost:8080/user/shoppingCart/sub`
3. **设置Headers**:
   - `Content-Type: application/json`
   - `token: your_jwt_token_here`
4. **设置Body** (raw JSON):
   ```json
   {
       "dishId": 1,
       "dishFlavor": "[{\"name\":\"辣度\",\"value\":\"微辣\"}]"
   }
   ```

## 测试场景

### 场景1：数量大于1的商品
1. 先添加商品到购物车（数量为3）
2. 调用减少接口
3. 验证数量变为2，商品仍在购物车中

### 场景2：数量等于1的商品
1. 先添加商品到购物车（数量为1）
2. 调用减少接口
3. 验证商品从购物车中被删除

### 场景3：不存在的商品
1. 调用减少接口（使用不存在的商品ID）
2. 验证接口正常返回，不会报错

### 场景4：带口味的商品
1. 添加带口味的商品到购物车
2. 使用相同的口味参数调用减少接口
3. 验证只有匹配口味的商品数量被减少

## 注意事项

1. **用户认证**: 必须先登录获取有效的JWT token
2. **口味匹配**: 如果商品有口味，必须提供完全匹配的口味参数
3. **商品类型**: dishId和setmealId只能提供其中一个
4. **返回数据**: 接口返回更新后的完整购物车列表
5. **口味标准化**: 系统会自动标准化口味参数，解决选择顺序不同的问题

## 错误处理

- 如果请求的商品不在购物车中，接口会正常返回，不会报错
- 如果token无效或过期，会返回401未授权错误
- 如果请求参数格式错误，会返回400错误
