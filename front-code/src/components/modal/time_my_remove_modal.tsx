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
import {useEffect, useState} from "react";
import {ClassTimeEntity} from "../../models/entity/class_time_entity.ts";
import {useSelector} from "react-redux";
import {UserEntity} from "../../models/entity/user_entity.ts";
import {DelClassTimeAPI, DelMyClassTimeAPI} from "../../interface/curriculum_api.ts";
import {BaseResponse} from "../../models/base_response.ts";

export function TimeMyRemoveModal({propOpen, classTime, emit, refresh}: {
    propOpen: boolean,
    classTime: ClassTimeEntity,
    emit: (data: boolean) => void,
    refresh: (data: boolean) => void
}) {
    const userEntity = useSelector((state: { userCurrent: UserEntity }) => state.userCurrent);

    const [open, setOpen] = useState<boolean>(false);

    useEffect(() => {
        setOpen(propOpen);
    }, [propOpen]);

    async function handleOk() {
        let getResp: BaseResponse<void> | undefined;
        if (userEntity.uuid === classTime.user_uuid) {
            getResp = await DelClassTimeAPI(classTime.class_time_market_uuid!);
        } else {
            getResp = await DelMyClassTimeAPI(classTime.class_time_market_uuid!);
        }
        if (getResp?.output === "Success") {
            message.success("删除成功");
            emit(false);
            refresh(true);
        } else {
            message.warning(getResp?.error_message);
        }
    }

    return(
        <Modal
            title="删除时间表"
            open={open}
            onOk={handleOk}
            onCancel={() => emit(false)}
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
            <div className={"text-center flex"}>
                <span>您确认</span>
                <span className={"text-red-500 font-bold"}>删除</span>
                <span className={"text-blue-500"}>{classTime.name}</span>
                <span>时间表吗？这将会失去很久很久......</span>
            </div>
            {
                userEntity.uuid === classTime.user_uuid ? (
                    <div className={"text-gray-500"}>
                        <span>您是该时间表的创建者，删除后所有的用户都会失去这个课表时间</span>
                    </div>
                ) : null
            }
        </Modal>
    );
}
