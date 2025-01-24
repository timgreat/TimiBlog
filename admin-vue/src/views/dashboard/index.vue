<template>
  <div class="dashboard-container">
    <div class="dashboard-text">
      博客管理系统
    </div>
    <div>
      本博客管理系统用于管理Timi的个人博客
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
export default {
  name: 'Dashboard',
  computed: {
    ...mapGetters([
      'name'
    ])
  }
}
</script>

<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}
</style>
