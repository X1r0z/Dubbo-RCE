# CVE-2023-23638

仅供学习研究

ZooKeeper 自备

测试环境为 Java 8, 其它版本尚未测试, 不保证可用性

复现时需要为 DemoComsumer 添加 VM 参数: `-Ddubbo.hessian.allowNonSerializable=true`, 详情参考 [https://su18.org/post/hessian/#serializable](https://su18.org/post/hessian/#serializable)

分析文章: [https://exp10it.io/2023/03/apache-dubbo-cve-2023-23638-%E5%88%86%E6%9E%90/](https://exp10it.io/2023/03/apache-dubbo-cve-2023-23638-%E5%88%86%E6%9E%90/)

POC 的本质是利用某个 class 修改 properties 以绕过限制, 代码给的是 JNDI 注入, 可以参考 [CVE-2023-23638 Apache Dubbo JavaNative反序列化漏洞分析](https://mp.weixin.qq.com/s?__biz=Mzg3OTcyNjM1MQ==&mid=2247483788&idx=1&sn=7954ad20fec203469a13a09050536a1c) 自行修改成反序列化的利用方式
