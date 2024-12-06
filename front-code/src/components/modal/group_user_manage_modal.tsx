/*
 * ***************************************************************************************
 * author: XiaoLFeng(https://www.x-lf.com)
 * about:
 *   The project contains the source code of com.xlf.schedule.
 *   All source code for this project is licensed under the MIT open source license.
 * licenseStatement:
 *   Copyright (c) 2016-2024 XiaoLFeng. All rights reserved.
 *   For more information about the MIT license, please view the LICENSE file
 *     in the project root directory or visit:
 *   https://opensource.org/license/MIT
 * disclaimer:
 *   Since this project is in the model design stage, we are not responsible for any losses
 *     caused by using this project for commercial purposes.
 *   If you modify the code and redistribute it, you need to clearly indicate what changes
 *     you made in the corresponding file.
 *   If you want to modify it for commercial use, please contact me.
 * ***************************************************************************************
 */

import {ScheduleGroupEntity} from "../../models/entity/schedule_group_entity.ts";
import {message, Modal} from "antd";
import {useEffect, useState} from "react";
import dayjs from "dayjs";
import {DeleteOutlined} from "@ant-design/icons";

export function GroupUserManageModal({propOpen, groupEntity, refresh, emit}: Readonly<{
    propOpen: boolean,
    groupEntity: ScheduleGroupEntity,
    refresh: (data: boolean) => void,
    emit: (data: boolean) => void
}>) {
    const [open, setOpen] = useState<boolean>(false);
    const [entity, setEntity] = useState<ScheduleGroupEntity>({} as ScheduleGroupEntity);

    useEffect(() => {
        setOpen(propOpen);
        setEntity(groupEntity);
    }, [groupEntity, propOpen]);

    useEffect(() => {
        if (propOpen) {
            // 操作获取用户列表
        }
    }, [propOpen]);

    async function handleOk() {
        message.success("编辑成功");
        emit(false);
        refresh(true);
    }

    return (
        <Modal
            open={open}
            title="成员管理"
            onOk={handleOk}
            onCancel={() => emit(false)}
            footer={
                <div className="flex gap-1 justify-end text-white">
                    <button onClick={() => emit(false)}
                            className="py-1.5 px-4 rounded-lg shadow bg-red-500 hover:bg-red-600 transition">
                        取消
                    </button>
                    <button onClick={handleOk}
                            className="py-1.5 px-4 rounded-lg shadow bg-sky-500 hover:bg-sky-600 transition">
                        完成
                    </button>
                </div>
            }
        >
            <div className="flex flex-col gap-3">
                <div className={"flex flex-col"}>
                    <div className={"flex flex-col border-2 rounded-lg p-3"}>
                        <div className="flex gap-2">
                            <div className="flex-1 flex gap-1 items-center">
                                <span className={"text-xl font-medium"}>{entity.name}</span>
                            </div>
                            <div className="flex-shrink-0 items-center flex gap-1">
                                {
                                    entity.tags.map((tag, index) => (
                                        <span key={`tag-${tag.trim()}-${index}`}
                                              className="px-2 py-1 bg-blue-100 text-blue-500 rounded-md text-xs">{tag}</span>
                                    ))
                                }
                            </div>
                        </div>
                        <div className="flex-1 gap-1 items-center">
                            <span
                                className={"text-gray-400 font-thin"}>{dayjs(entity.created_at).format("YYYY年MM月DD日 HH时")}</span>
                        </div>
                    </div>
                </div>
                <div className={"flex flex-col"}>
                    <div className="text-xl font-bold">小组成员</div>
                    <div className="overflow-x-auto">
                        <table className="min-w-full divide-y-2 divide-gray-200 bg-white text-sm">
                            <thead className="text-left">
                                <tr>
                                    <th className="whitespace-nowrap px-4 py-2 font-medium text-gray-900">序号</th>
                                    <th className="whitespace-nowrap px-4 py-2 font-medium text-gray-900">组员信息</th>
                                    <th className="whitespace-nowrap px-4 py-2 font-medium text-gray-900">权限</th>
                                    <th className="whitespace-nowrap px-4 py-2 font-medium text-gray-900 text-end">操作</th>
                                </tr>
                            </thead>
                            <tbody className="divide-y divide-gray-200">
                                <tr className="odd:bg-gray-50">
                                    <td className="whitespace-nowrap px-4 py-2 font-medium text-gray-900">1</td>
                                    <td className="whitespace-nowrap px-4 py-2 text-gray-700">xiao_lfeng</td>
                                    <td className="whitespace-nowrap px-4 py-2 text-gray-700">组长</td>
                                    <td className="whitespace-nowrap px-4 py-2 flex space-x-1 justify-end">
                                        <button
                                            onClick={() => message.warning("暂不支持")}
                                            className="transition bg-red-500 hover:bg-red-600 text-white rounded-md p-1.5 flex items-center">
                                            <DeleteOutlined />
                                        </button>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </Modal>
    );
}
