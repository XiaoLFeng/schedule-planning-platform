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
import {ClassGradeEntity} from "../../models/entity/class_grade_entity.ts";
import {DeleteClassGradeAPI} from "../../interface/curriculum_api.ts";

export function CurriculumDelModal({propOpen, classGrade, emit, refresh, reset}: {
    propOpen: boolean;
    classGrade: ClassGradeEntity;
    emit: (open: boolean) => void;
    refresh: (data: boolean) => void;
    reset: (data: string) => void;
}) {
    const [open, setOpen] = useState<boolean>(false);

    useEffect(() => {
        setOpen(propOpen);
    }, [propOpen]);

    async function handleOk() {
        const getResp = await DeleteClassGradeAPI(classGrade.class_grade_uuid);
        if (getResp?.output === "Success") {
            message.success("删除成功");
            refresh(true);
            emit(false);
            reset("");
        } else {
            message.warning(getResp?.error_message);
        }
    }

    return (
        <Modal
            title="删除课程"
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
                <span className={"text-blue-500"}>{classGrade.nickname}</span>
                <span>课程表吗？这将会失去很久很久......</span>
            </div>
        </Modal>
    );
}
