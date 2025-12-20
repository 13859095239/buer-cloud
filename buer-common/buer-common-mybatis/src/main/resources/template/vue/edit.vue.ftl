<!--
  - ${cnTableSubName}编辑页
  -
  - @author ${author}
  - @since ${since}
  -->
<template>
  <BasicModal
    v-model:methods="modalMethods"
    v-bind="modalProps">
    <BasicForm
      v-model="form"
      v-model:methods="formMethods"
      v-bind="formProps" />
  </BasicModal>
</template>
<script setup lang="ts">
import { BasicModal, BasicModalProps, ModalMethods } from '/@/components/Modal';
import { BasicForm, BasicFormProps } from '/@/components/Form';
import { getCodeRuleById, saveCodeRule, updateCodeRuleById } from '/@/api/system/codeRule';
import { FormMethods } from '/@/components/Form/src/types/form';
import { nextTick, ref, shallowRef } from 'vue';
import { saveSuccess } from '/@/utils/prompt';
import { useDictRadio } from '/@/components/Dict/src/hooks/useDictRadio';

// From 组件方法
let formMethods = ref<FormMethods>({} as FormMethods);
// Modal 组件方法
let modalMethods = ref<ModalMethods>({} as ModalMethods);

// 表单数据
const form = ref<Recordable>({});

/** 编号规则id */
let id: string | undefined = undefined;
const emit = defineEmits<{
  (e: 'saved'): void;
}>();

/** Modal 组件配置 */
const modalProps = shallowRef<BasicModalProps>({
  title: `${id ? '新增' : '编辑'}编号规则`,
  width: 'column-1',
  onSaved: async () => {
    saveOrUpdate().then(() => {
      saveSuccess();
      modalMethods.value.close();
      emit('saved');
    });
  },
  onClosed: () => {}
});

/** Form 组件配置 */
const formProps = shallowRef<BasicFormProps>({
  labelWidth: 130,
  formSchemas: [
    {
      label: '编码',
      field: 'code',
      component: 'Input',
      rules: [{ required: true }]
    },
    {
      label: '名称',
      field: 'name',
      component: 'Input',
      rules: [{ required: true }]
    },
    {
      label: '流水号前缀',
      field: 'prefix',
      component: 'Input',
      rules: [{ required: true }]
    },
    {
      label: '流水号日期类型',
      field: 'codeRuleDateType',
      component: 'Radio',
      radioProps: useDictRadio('flowCandidateType'),
      rules: [{ required: true }]
    },
    {
      label: '流水号位数',
      field: 'digit',
      component: 'Input',
      rules: [{ required: true }]
    },
    {
      label: '描述',
      field: 'depiction',
      component: 'InputTextArea'
    },
    {
      label: '排序',
      field: 'sort',
      component: 'InputNumber',
      rules: [{ required: true, min: 0, max: 99 }]
    }
  ]
});

/** 清空数据 */
const clearData = () => {
  form.value = {};
};

/** 获取数据 */
const getData = async (id: string) => {
  const data = await getCodeRuleById(id);
  form.value = data;
};

/** 保存数据 */
const saveOrUpdate = async () => {
  // 表单验证
  const validateResult = await formMethods.value.validate();
  if (!validateResult) {
    return Promise.reject();
  }
  if (!id) {
    return saveCodeRule(form.value!);
  } else {
    return updateCodeRuleById(form.value!);
  }
};

/** 显示查看视图 */
const show = (_id: string) => {
  id = _id;
  formProps.value.editable = false;
  clearData();
  modalMethods.value.show();
  nextTick(() => {
    getData(id!);
  });
};

/** 显示新增视图 */
const showAdd = () => {
  clearData();
  id = undefined;
  formProps.value.editable = true;
  modalMethods.value.show();
};

/** 显示编辑视图 */
const showEdit = (_id: string) => {
  id = _id;
  formProps.value.editable = true;
  clearData();
  modalMethods.value.show();
  nextTick(() => {
    getData(id!);
  });
};

// 对外暴露组件方法
defineExpose({
  show,
  showAdd,
  showEdit
});
</script>