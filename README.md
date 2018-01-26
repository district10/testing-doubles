## 总结

**mock: 多半是假的**

- 你要测试那个实体, 得是真的; 但它后面所依赖的一大坨, 没必要是真的 (我们假定它们的逻辑已经是正确的了)
- 没必要是真的的东西, 就 stub 它的一些行为, 没必要真的让它们跑; 
- mock 它们, 它们纯粹是空壳; 你要做的, 是给它填填补补, 够用来测试真东西就行;

> 总结一: 测的是真的, 依赖的是假的; 空壳子补上几个洞, 就能满足真东西, 就够测它的逻辑.

(如果你想要一个 mock 真的运行后面的逻辑, 也可以, 用 doCallRealMethod 就行.)

**spy: 多半是真的**

- 你要测得那个实体, 不止依赖于后面那一坨, 可能还自我纠缠;
- 你是个真的, 你所依赖的那些属于自己的逻辑, 可能是耗时的, 没必要让它们跑;
- 你要 stub 这些逻辑? 好, 你不是你, 你是被 spy 了的一个 proxy, 这样才能 stub 自己的部分逻辑;

> 总结二: 测的是真的, 依赖的是真的; 就需要把真的东西封装一层, 隔离出那些耗时缺不想测得逻辑.

## Ref Docs

- http://www.baeldung.com/mockito-annotations

---

A simple JUnit Jupiter extension to integrate Mockito into JUnit Jupiter tests somewhat simpler.

The `MockitoExtension` showcases the `TestInstancePostProcessor` and `ParameterResolver`
extension APIs of JUnit Jupiter by providing dependency injection support at the field level
and at the method parameter level via Mockito 2.x's `@Mock` annotation.

See also:

- [Mockito issue #390](https://github.com/mockito/mockito/issues/390)
- [Mockito issue #438](https://github.com/mockito/mockito/issues/438)
- [Mockito issue #445](https://github.com/mockito/mockito/issues/445)
