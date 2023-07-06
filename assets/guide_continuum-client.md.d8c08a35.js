import{_ as s,o as n,c as a,d as o}from"./app.3d808b2e.js";const F=JSON.parse('{"title":"Continuum Client","description":"","frontmatter":{"outline":"deep"},"headers":[{"level":2,"title":"Continuum Client for Javascript","slug":"continuum-client-for-javascript","link":"#continuum-client-for-javascript","children":[]}],"relativePath":"guide/continuum-client.md"}'),l={name:"guide/continuum-client.md"},t=o(`<h1 id="continuum-client" tabindex="-1">Continuum Client <a class="header-anchor" href="#continuum-client" aria-hidden="true">#</a></h1><p>The Continuum client allows you to connect to a Continuum cluster and interact with the microservices. There are multiple flavors of the client depending on the language you are using.</p><h2 id="continuum-client-for-javascript" tabindex="-1">Continuum Client for Javascript <a class="header-anchor" href="#continuum-client-for-javascript" aria-hidden="true">#</a></h2><p>Usage in Node Js:</p><div class="language-javascript"><button title="Copy Code" class="copy"></button><span class="lang">javascript</span><pre class="shiki material-theme-palenight" tabindex="0"><code><span class="line"></span>
<span class="line"><span style="color:#676E95;font-style:italic;">// this only needs to be done once in your application</span></span>
<span class="line"><span style="color:#89DDFF;font-style:italic;">import</span><span style="color:#A6ACCD;"> </span><span style="color:#89DDFF;">{</span><span style="color:#F07178;"> </span><span style="color:#A6ACCD;">Continuum</span><span style="color:#F07178;"> </span><span style="color:#89DDFF;">}</span><span style="color:#A6ACCD;"> </span><span style="color:#89DDFF;font-style:italic;">from</span><span style="color:#A6ACCD;"> </span><span style="color:#89DDFF;">&#39;</span><span style="color:#C3E88D;">@kinotic/continuum-client</span><span style="color:#89DDFF;">&#39;</span></span>
<span class="line"><span style="color:#89DDFF;font-style:italic;">import</span><span style="color:#A6ACCD;"> </span><span style="color:#89DDFF;">{</span><span style="color:#F07178;"> </span><span style="color:#A6ACCD;">WebSocket</span><span style="color:#F07178;"> </span><span style="color:#89DDFF;">}</span><span style="color:#A6ACCD;"> </span><span style="color:#89DDFF;font-style:italic;">from</span><span style="color:#A6ACCD;"> </span><span style="color:#89DDFF;">&#39;</span><span style="color:#C3E88D;">ws</span><span style="color:#89DDFF;">&#39;</span></span>
<span class="line"></span>
<span class="line"><span style="color:#A6ACCD;">Object</span><span style="color:#89DDFF;">.</span><span style="color:#82AAFF;">assign</span><span style="color:#A6ACCD;">(global</span><span style="color:#89DDFF;">,</span><span style="color:#A6ACCD;"> </span><span style="color:#89DDFF;">{</span><span style="color:#A6ACCD;"> WebSocket</span><span style="color:#89DDFF;">}</span><span style="color:#A6ACCD;">)</span></span>
<span class="line"></span>
<span class="line"><span style="color:#A6ACCD;">Continuum</span><span style="color:#89DDFF;">.</span><span style="color:#82AAFF;">connect</span><span style="color:#A6ACCD;">(</span><span style="color:#89DDFF;">&#39;</span><span style="color:#C3E88D;">ws://127.0.0.1:58503/v1</span><span style="color:#89DDFF;">&#39;</span><span style="color:#89DDFF;">,</span><span style="color:#A6ACCD;"> </span><span style="color:#89DDFF;">&#39;</span><span style="color:#C3E88D;">admin</span><span style="color:#89DDFF;">&#39;</span><span style="color:#89DDFF;">,</span><span style="color:#A6ACCD;"> </span><span style="color:#89DDFF;">&#39;</span><span style="color:#C3E88D;">structures</span><span style="color:#89DDFF;">&#39;</span><span style="color:#A6ACCD;">)</span></span>
<span class="line"></span>
<span class="line"></span>
<span class="line"></span></code></pre></div>`,5),e=[t];function p(c,r,i,D,y,u){return n(),a("div",null,e)}const A=s(l,[["render",p]]);export{F as __pageData,A as default};
