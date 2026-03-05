# 优化模块 3：Vite 构建配置

## 优化内容

### 1. 安装依赖
- 安装 `vite-plugin-compression` 插件用于 gzip 压缩

### 2. 配置代码分割和 tree-shaking
- 在 `vite.config.js` 中添加 `build` 配置
- 启用代码分割，按路由拆分代码
- 配置合理的 chunk 大小

### 3. 启用 gzip 压缩
- 配置 `vite-plugin-compression` 插件
- 支持 gzip 和 brotli 压缩
- 配置压缩阈值和文件类型

### 4. 优化构建速度和输出大小
- 配置缓存策略
- 优化 ES 构建目标
- 配置 sourcemap 生成策略

## 具体修改

### 修改文件：`vite.config.js`
1. 导入 `vite-plugin-compression` 插件
2. 添加 `build` 配置项
3. 配置代码分割策略
4. 配置 gzip 压缩
5. 优化构建参数

### 修改文件：`package.json`
1. 添加 `vite-plugin-compression` 到开发依赖

## 测试用例

1. **验证构建过程无错误**
   - 运行 `npm run build` 命令
   - 检查构建过程是否顺利完成

2. **检查构建输出大小**
   - 对比优化前后的构建产物大小
   - 验证压缩文件是否生成

3. **测试构建速度提升**
   - 记录优化前后的构建时间
   - 验证构建速度是否提升

## 预期效果

- 构建速度提升 25% 以上
- 输出文件大小减少 20% 以上
- 生成压缩文件，提高资源加载速度
- 代码分割后，首屏加载速度提升

## 实施步骤

1. 安装 `vite-plugin-compression` 插件
2. 修改 `vite.config.js` 配置文件
3. 运行构建命令验证优化效果
4. 对比优化前后的性能数据
5. 记录优化结果