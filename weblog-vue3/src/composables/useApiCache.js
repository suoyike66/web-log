// API请求缓存机制

// 缓存存储
const cacheStorage = new Map();

// 缓存配置
const cacheConfig = {
  // 默认缓存过期时间（毫秒）
  defaultExpireTime: 5 * 60 * 1000, // 5分钟
  // 最大缓存数量
  maxCacheSize: 100,
};

/**
 * 生成缓存键
 * @param {string} url - 请求URL
 * @param {object} params - 请求参数
 * @returns {string} 缓存键
 */
export function generateCacheKey(url, params = {}) {
  return `${url}_${JSON.stringify(params)}`;
}

/**
 * 设置缓存
 * @param {string} key - 缓存键
 * @param {any} data - 缓存数据
 * @param {number} expireTime - 过期时间（毫秒）
 */
export function setCache(key, data, expireTime = cacheConfig.defaultExpireTime) {
  // 检查缓存大小，如果超过最大限制，删除最早的缓存
  if (cacheStorage.size >= cacheConfig.maxCacheSize) {
    const firstKey = cacheStorage.keys().next().value;
    cacheStorage.delete(firstKey);
  }

  const cacheData = {
    data,
    timestamp: Date.now(),
    expireTime,
  };

  cacheStorage.set(key, cacheData);
}

/**
 * 获取缓存
 * @param {string} key - 缓存键
 * @returns {any|null} 缓存数据，如果缓存不存在或已过期则返回null
 */
export function getCache(key) {
  const cacheData = cacheStorage.get(key);

  if (!cacheData) {
    return null;
  }

  const { data, timestamp, expireTime } = cacheData;

  // 检查缓存是否过期
  if (Date.now() - timestamp > expireTime) {
    cacheStorage.delete(key);
    return null;
  }

  return data;
}

/**
 * 删除缓存
 * @param {string} key - 缓存键
 */
export function deleteCache(key) {
  cacheStorage.delete(key);
}

/**
 * 清除所有缓存
 */
export function clearCache() {
  cacheStorage.clear();
}

/**
 * 获取缓存大小
 * @returns {number} 缓存大小
 */
export function getCacheSize() {
  return cacheStorage.size;
}

/**
 * 创建带缓存的axios实例
 * @param {object} axiosInstance - 原始axios实例
 * @returns {object} 带缓存的axios实例
 */
export function createCachedAxios(axiosInstance) {
  const cachedAxios = {
    // 复制原始axios实例的属性和方法
    ...axiosInstance,

    // 带缓存的get方法
    get: function (url, config = {}) {
      const { cache = false, expireTime } = config;

      if (!cache) {
        return axiosInstance.get(url, config);
      }

      const cacheKey = generateCacheKey(url, config.params || {});
      const cachedData = getCache(cacheKey);

      if (cachedData) {
        return Promise.resolve(cachedData);
      }

      return axiosInstance.get(url, config).then(response => {
        setCache(cacheKey, response, expireTime);
        return response;
      });
    },

    // 带缓存的post方法
    post: function (url, data = {}, config = {}) {
      const { cache = false, expireTime } = config;

      if (!cache) {
        return axiosInstance.post(url, data, config);
      }

      const cacheKey = generateCacheKey(url, data);
      const cachedData = getCache(cacheKey);

      if (cachedData) {
        return Promise.resolve(cachedData);
      }

      return axiosInstance.post(url, data, config).then(response => {
        setCache(cacheKey, response, expireTime);
        return response;
      });
    },

    // 带缓存的put方法
    put: function (url, data = {}, config = {}) {
      const { cache = false, expireTime } = config;

      if (!cache) {
        return axiosInstance.put(url, data, config);
      }

      const cacheKey = generateCacheKey(url, data);
      const cachedData = getCache(cacheKey);

      if (cachedData) {
        return Promise.resolve(cachedData);
      }

      return axiosInstance.put(url, data, config).then(response => {
        setCache(cacheKey, response, expireTime);
        return response;
      });
    },

    // 带缓存的delete方法
    delete: function (url, config = {}) {
      const { cache = false, expireTime } = config;

      if (!cache) {
        return axiosInstance.delete(url, config);
      }

      const cacheKey = generateCacheKey(url, config.params || {});
      const cachedData = getCache(cacheKey);

      if (cachedData) {
        return Promise.resolve(cachedData);
      }

      return axiosInstance.delete(url, config).then(response => {
        setCache(cacheKey, response, expireTime);
        return response;
      });
    },
  };

  return cachedAxios;
}
