<template>
  <div id="app">
    <router-view />
    <div class="footer">
      <!-- 备案号链接 -->
      <a href="https://beian.miit.gov.cn/" target="_blank" class="balink footer">赣ICP备2024049101号-1</a>
    </div>
  </div>
</template>

<script>
export default {
  name: 'App',
  metaInfo() {
    return {
      title: this.$store.state.settings.dynamicTitle && this.$store.state.settings.title,
      titleTemplate: title => {
        return title ? `${title} - ${process.env.VUE_APP_TITLE}` : process.env.VUE_APP_TITLE
      }
    }
  }
}
</script>
