// **********************************************************************
//
// Copyright (c) 2010-2016 iflytek, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************

#pragma once

//#include <Ice/Identity.ice>

module skynet
{
module ant
{
module rpc
{
module core
{
sequence<byte> byteArray;

	/**
	* RPC 状态
	**/
struct RpcStatus
{
	/**
	* 跟踪id（可以是业务Id）
	**/
	string trackId;
	
	/**
	*  会话Id
	**/
	string sessionId;
	/**
	* 状态 0:成功， 其他: 异常
	**/
	int ok; 
	
	/**
	* 具体自定义状态
	**/
	string status; 
};
	/**
	* RPC 结果
	**/
struct RpcResult
{
	/**
	* 跟踪id（可以是业务Id）
	**/
	string trackId;
	
	/**
	* 参数
	**/
	string sessionId;
	
	/**
	* 会话结果
	**/
	byteArray result;
	
	/**
	* 结果响应上下文
	**/
	byteArray context;
};
  
/**
*会话响应（回调）
**/
interface RpcResponse
{
	 /**
     * 会话结果回调 
     * @param result 响应结果 
     * @return 会话状态
     */
	RpcStatus callback(RpcResult result);
};

	/**
	* Rpc请求参数
	**/
struct RpcParam
{
	/**
	* 跟踪id（可以是业务Id）
	**/
	string trackId;
	
	/**
	* 参数
	**/
	string parameters;
	
	/**
	* 请求来自IP
	**/
	string from;
	
	/**
	* 会话自定义上下文
	**/
	byteArray context;
	
	/**
	* 结果响应回调代理
	**/
	RpcResponse* callbackProxy;
};
 

/**
*会话请求 
**/
interface RpcRequest
{  
   	/**
     * 会话开始
     * @param param 会话请求参数 
     * @return 会话状态
     */
    RpcStatus begin(RpcParam param);

   /**
     * 会话进行中
     * @param sessionId 会话Id
     * @param content 会话内容
     * @return 会话状态
     */ 
	RpcStatus post(string sessionId, byteArray content);

    /**
     * 会话结束
     * @param sessionId 会话Id
     * @return 会话状态
     */
	RpcStatus end(string sessionId); 
	
    /**
     * 通用远程方法
     * @param parameter 调用参数
     * @return 调用结果
     */
	byteArray call(byteArray parameter);
		
}; 
}; 
}; 
}; 
};
