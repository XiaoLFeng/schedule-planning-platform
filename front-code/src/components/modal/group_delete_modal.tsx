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

import {message, Modal} from "antd";
import {ScheduleGroupEntity} from "../../models/entity/schedule_group_entity.ts";
import {DeleteScheduleGroupAPI} from "../../interface/schedule_api.ts";

export function GroupDeleteModal({propOpen, groupEntity, refresh, emit}: {
    propOpen: boolean,
    groupEntity: ScheduleGroupEntity,
    refresh: (data: boolean) => void,
    emit: (data: boolean) => void
}) {

    async function handleOk() {
        const getResp = await DeleteScheduleGroupAPI(groupEntity.group_uuid);
        if (getResp?.output === "Success") {
            message.success("删除成功");
            emit(false);
            refresh(true);
        } else {
            message.warning(getResp?.error_message);
        }
    }

    return (
        <Modal
            title="删除分组"
            open={propOpen}
            footer={
                <div className="flex gap-1 justify-end text-white">
                    <button
                        onClick={() => emit(false)}
                        className="py-1.5 px-4 rounded-lg shadow bg-sky-500 hover:bg-sky-600 transition"
                    >
                        取消
                    </button>
                    <button
                        onClick={handleOk}
                        className="py-1.5 px-4 rounded-lg shadow bg-red-500 hover:bg-red-600 transition"
                    >
                        确认
                    </button>
                </div>
            }
        >
            <div className={"text-center"}>
                <div className={"flex"}>
                    <span>您确认</span>
                    <span className={"text-red-500 font-bold"}>删除</span>
                    <span className={"text-blue-500"}>{groupEntity.name}</span>
                    <span>分组吗？这将会失去很久很久......</span>
                </div>
            </div>
            <div className={"text-center text-red-500 mt-2"}>
                <span>该操作也会将分组内的所有日程删除！</span>
            </div>
        </Modal>
    );
}
