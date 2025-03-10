import { defineConfig } from 'vitepress'

// https://vitepress.vuejs.org/reference/site-config
export default defineConfig({
  title: "Continuum",
  description: "Insanely versatile IoT and Microservice framework",
  base: '/continuum-framework/website/',
  themeConfig: {
    logo: '/icon.png',
    // https://vitepress.vuejs.org/reference/default-theme-config

    nav: nav(),

    sidebar: {
      '/guide/': sidebarGuide(),
      '/reference/': sidebarReference()
    },

    socialLinks: [
      { icon: 'github', link: ' https://github.com/Kinotic-Foundation/continuum-framework' }
    ],
    footer: {
      message: 'Released under the Apache License.',
      copyright: 'Copyright © 2018-present Kinotic Foundation'
    }
  }
})

function nav() {
  return [
    { text: 'Guide', link: '/guide/overview', activeMatch: '/guide/' },
    {
      text: 'Reference',
      link: '/reference/continuum-config',
      activeMatch: '/reference/'
    },
    {
      text: 'Test Status',
      link: 'https://kinotic-foundation.github.io/continuum-framework/allure' // Fully qualified URL
    }
  ]
}

function sidebarGuide() {
  return [
    {
      text: 'Introduction',
      items: [
        { text: 'What is Continuum?', link: '/guide/overview' },
        { text: 'Getting Started', link: '/guide/getting-started' }
      ]
    },
    {
      text: 'Details',
      items: [
        { text: 'Command Line', link: '/guide/cli-overview' },
        { text: 'Services', link: '/guide/services' }
      ]
    }
  ]
}

function sidebarReference() {
  return [
    {
      text: 'Reference',
      items: [
        { text: 'Continuum Config', link: '/reference/continuum-config' },
        { text: 'C3 IDL', link: '/reference/c3-idl' }
      ]
    },
    {
      text: 'API',
      items: [
        { text: 'Javadoc', link: '/reference/javadoc' }
      ]
    }
  ]
}
