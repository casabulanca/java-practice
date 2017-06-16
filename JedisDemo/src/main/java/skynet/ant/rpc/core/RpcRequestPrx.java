// **********************************************************************
//
// Copyright (c) 2003-2013 ZeroC, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************
//
// Ice version 3.5.1
//
// <auto-generated>
//
// Generated from file `RpcChannel.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package skynet.ant.rpc.core;

/**
 * 会话请求 
 **/
public interface RpcRequestPrx extends Ice.ObjectPrx
{
    /**
     * 会话开始
     * @param param 会话请求参数 
     * @return 会话状态
     **/
    public RpcStatus begin(RpcParam param);

    /**
     * 会话开始
     * @param param 会话请求参数 
     * @param __ctx The Context map to send with the invocation.
     * @return 会话状态
     **/
    public RpcStatus begin(RpcParam param, java.util.Map<String, String> __ctx);

    /**
     * 会话开始
     * @param param 会话请求参数 
     * @return The asynchronous result object.
     **/
    public Ice.AsyncResult begin_begin(RpcParam param);

    /**
     * 会话开始
     * @param param 会话请求参数 
     * @param __ctx The Context map to send with the invocation.
     * @return The asynchronous result object.
     **/
    public Ice.AsyncResult begin_begin(RpcParam param, java.util.Map<String, String> __ctx);

    /**
     * 会话开始
     * @param param 会话请求参数 
     * @param __cb The asynchronous callback object.
     * @return The asynchronous result object.
     **/
    public Ice.AsyncResult begin_begin(RpcParam param, Ice.Callback __cb);

    /**
     * 会话开始
     * @param param 会话请求参数 
     * @param __ctx The Context map to send with the invocation.
     * @param __cb The asynchronous callback object.
     * @return The asynchronous result object.
     **/
    public Ice.AsyncResult begin_begin(RpcParam param, java.util.Map<String, String> __ctx, Ice.Callback __cb);

    /**
     * 会话开始
     * @param param 会话请求参数 
     * @param __cb The asynchronous callback object.
     * @return The asynchronous result object.
     **/
    public Ice.AsyncResult begin_begin(RpcParam param, Callback_RpcRequest_begin __cb);

    /**
     * 会话开始
     * @param param 会话请求参数 
     * @param __ctx The Context map to send with the invocation.
     * @param __cb The asynchronous callback object.
     * @return The asynchronous result object.
     **/
    public Ice.AsyncResult begin_begin(RpcParam param, java.util.Map<String, String> __ctx, Callback_RpcRequest_begin __cb);

    /**
     * 会话开始
     * @param __result The asynchronous result object.
     * @return 会话状态
     **/
    public RpcStatus end_begin(Ice.AsyncResult __result);

    /**
     * 会话进行中
     * @param sessionId 会话Id
     * @param content 会话内容
     * @return 会话状态
     **/
    public RpcStatus post(String sessionId, byte[] content);

    /**
     * 会话进行中
     * @param sessionId 会话Id
     * @param content 会话内容
     * @param __ctx The Context map to send with the invocation.
     * @return 会话状态
     **/
    public RpcStatus post(String sessionId, byte[] content, java.util.Map<String, String> __ctx);

    /**
     * 会话进行中
     * @param sessionId 会话Id
     * @return The asynchronous result object.
     **/
    public Ice.AsyncResult begin_post(String sessionId, byte[] content);

    /**
     * 会话进行中
     * @param sessionId 会话Id
     * @param __ctx The Context map to send with the invocation.
     * @return The asynchronous result object.
     **/
    public Ice.AsyncResult begin_post(String sessionId, byte[] content, java.util.Map<String, String> __ctx);

    /**
     * 会话进行中
     * @param sessionId 会话Id
     * @param __cb The asynchronous callback object.
     * @return The asynchronous result object.
     **/
    public Ice.AsyncResult begin_post(String sessionId, byte[] content, Ice.Callback __cb);

    /**
     * 会话进行中
     * @param sessionId 会话Id
     * @param __ctx The Context map to send with the invocation.
     * @param __cb The asynchronous callback object.
     * @return The asynchronous result object.
     **/
    public Ice.AsyncResult begin_post(String sessionId, byte[] content, java.util.Map<String, String> __ctx, Ice.Callback __cb);

    /**
     * 会话进行中
     * @param sessionId 会话Id
     * @param __cb The asynchronous callback object.
     * @return The asynchronous result object.
     **/
    public Ice.AsyncResult begin_post(String sessionId, byte[] content, Callback_RpcRequest_post __cb);

    /**
     * 会话进行中
     * @param sessionId 会话Id
     * @param __ctx The Context map to send with the invocation.
     * @param __cb The asynchronous callback object.
     * @return The asynchronous result object.
     **/
    public Ice.AsyncResult begin_post(String sessionId, byte[] content, java.util.Map<String, String> __ctx, Callback_RpcRequest_post __cb);

    /**
     * 会话进行中
     * @param __result The asynchronous result object.
     * @return 会话状态
     **/
    public RpcStatus end_post(Ice.AsyncResult __result);

    /**
     * 会话结束
     * @param sessionId 会话Id
     * @return 会话状态
     **/
    public RpcStatus end(String sessionId);

    /**
     * 会话结束
     * @param sessionId 会话Id
     * @param __ctx The Context map to send with the invocation.
     * @return 会话状态
     **/
    public RpcStatus end(String sessionId, java.util.Map<String, String> __ctx);

    /**
     * 会话结束
     * @param sessionId 会话Id
     * @return The asynchronous result object.
     **/
    public Ice.AsyncResult begin_end(String sessionId);

    /**
     * 会话结束
     * @param sessionId 会话Id
     * @param __ctx The Context map to send with the invocation.
     * @return The asynchronous result object.
     **/
    public Ice.AsyncResult begin_end(String sessionId, java.util.Map<String, String> __ctx);

    /**
     * 会话结束
     * @param sessionId 会话Id
     * @param __cb The asynchronous callback object.
     * @return The asynchronous result object.
     **/
    public Ice.AsyncResult begin_end(String sessionId, Ice.Callback __cb);

    /**
     * 会话结束
     * @param sessionId 会话Id
     * @param __ctx The Context map to send with the invocation.
     * @param __cb The asynchronous callback object.
     * @return The asynchronous result object.
     **/
    public Ice.AsyncResult begin_end(String sessionId, java.util.Map<String, String> __ctx, Ice.Callback __cb);

    /**
     * 会话结束
     * @param sessionId 会话Id
     * @param __cb The asynchronous callback object.
     * @return The asynchronous result object.
     **/
    public Ice.AsyncResult begin_end(String sessionId, Callback_RpcRequest_end __cb);

    /**
     * 会话结束
     * @param sessionId 会话Id
     * @param __ctx The Context map to send with the invocation.
     * @param __cb The asynchronous callback object.
     * @return The asynchronous result object.
     **/
    public Ice.AsyncResult begin_end(String sessionId, java.util.Map<String, String> __ctx, Callback_RpcRequest_end __cb);

    /**
     * 会话结束
     * @param __result The asynchronous result object.
     * @return 会话状态
     **/
    public RpcStatus end_end(Ice.AsyncResult __result);

    /**
     * 通用远程方法
     * @param parameter 调用参数
     * @return 调用结果
     **/
    public byte[] call(byte[] parameter);

    /**
     * 通用远程方法
     * @param parameter 调用参数
     * @param __ctx The Context map to send with the invocation.
     * @return 调用结果
     **/
    public byte[] call(byte[] parameter, java.util.Map<String, String> __ctx);

    /**
     * 通用远程方法
     * @param parameter 调用参数
     * @return The asynchronous result object.
     **/
    public Ice.AsyncResult begin_call(byte[] parameter);

    /**
     * 通用远程方法
     * @param parameter 调用参数
     * @param __ctx The Context map to send with the invocation.
     * @return The asynchronous result object.
     **/
    public Ice.AsyncResult begin_call(byte[] parameter, java.util.Map<String, String> __ctx);

    /**
     * 通用远程方法
     * @param parameter 调用参数
     * @param __cb The asynchronous callback object.
     * @return The asynchronous result object.
     **/
    public Ice.AsyncResult begin_call(byte[] parameter, Ice.Callback __cb);

    /**
     * 通用远程方法
     * @param parameter 调用参数
     * @param __ctx The Context map to send with the invocation.
     * @param __cb The asynchronous callback object.
     * @return The asynchronous result object.
     **/
    public Ice.AsyncResult begin_call(byte[] parameter, java.util.Map<String, String> __ctx, Ice.Callback __cb);

    /**
     * 通用远程方法
     * @param parameter 调用参数
     * @param __cb The asynchronous callback object.
     * @return The asynchronous result object.
     **/
    public Ice.AsyncResult begin_call(byte[] parameter, Callback_RpcRequest_call __cb);

    /**
     * 通用远程方法
     * @param parameter 调用参数
     * @param __ctx The Context map to send with the invocation.
     * @param __cb The asynchronous callback object.
     * @return The asynchronous result object.
     **/
    public Ice.AsyncResult begin_call(byte[] parameter, java.util.Map<String, String> __ctx, Callback_RpcRequest_call __cb);

    /**
     * 通用远程方法
     * @param __result The asynchronous result object.
     * @return 调用结果
     **/
    public byte[] end_call(Ice.AsyncResult __result);
}