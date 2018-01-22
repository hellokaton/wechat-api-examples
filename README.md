# wechat-api examples

这里项目里有诸多 [wechat-api](https://github.com/biezhi/wechat-api) 的示例程序，你可以使用他们进行改造或参考使用代码。

[![](https://img.shields.io/travis/biezhi/wechat-api-examples.svg)](https://travis-ci.org/biezhi/wechat-api-examples)
[![](https://img.shields.io/badge/license-MIT-FF0080.svg)](https://github.com/biezhi/wechat-api-examples/blob/master/LICENSE)
[![@biezhi on zhihu](https://img.shields.io/badge/zhihu-%40biezhi-red.svg)](https://www.zhihu.com/people/biezhi)
[![](https://img.shields.io/github/followers/biezhi.svg?style=social&label=Follow%20Me)](https://github.com/biezhi)

## 机器人示例

- [图灵机器人](tuling-bot)
- [茉莉机器人](moli-bot)
- [喝水机器人](https://github.com/biezhi/wechat-api-examples/tree/master/drink-bot/src/main/java/me/biezhi/wechat/examples)

## 如何打包？

该项目基于 Java8、Maven、lombok 构建，请确保你已有该环境。

```bash
cd 某个bot目录
mvn clean assembly:assembly
```

会在 `target` 目录下生成一个 `xxx-1.0-SNAPSHOT-jar-with-dependencies.jar`，
在服务器上运行这个 `jar` 包即可。

## 开源协议

[MIT](https://github.com/biezhi/wechat-api-examples/blob/master/LICENSE)