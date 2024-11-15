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

import {DatePicker, message, Modal} from "antd";
import {useEffect, useState} from "react";
import {Dayjs} from "dayjs";
import {ClassGradeDTO} from "../../models/dto/class_grade_create_dto.ts";
import {CreateClassGradeAPI} from "../../interface/curriculum_api.ts";
import {ListCurriculumTimeEntity} from "../../models/entity/list_curriculum_time_entity.ts";
import {useNavigate} from "react-router-dom";

export function CurriculumAddModal({propOpen, timeList, emit, refresh}: {
    propOpen: boolean;
    timeList: ListCurriculumTimeEntity[];
    emit: (open: boolean) => void;
    refresh: (data: boolean) => void
}) {
    const navigate = useNavigate();

    const [open, setOpen] = useState(false);
    const [classGradeAdd, setClassGradeAdd] = useState<ClassGradeDTO>({} as ClassGradeDTO);

    useEffect(() => {
        setOpen(propOpen);
    }, [propOpen]);

    useEffect(() => {
        if (classGradeAdd.class_time_uuid === "system-add") {
            navigate("/dashboard/curriculum/time");
        }
    }, [classGradeAdd.class_time_uuid, navigate]);

    async function handleOk() {
        const getResp = await CreateClassGradeAPI(classGradeAdd);
        if (getResp?.output === "Success") {
            message.success("添加成功");
            refresh(true);
            emit(false);
        } else {
            message.warning(getResp?.error_message);
        }
    }

    function onStartTime(_date: Dayjs, dateString: string | string[]) {
        setClassGradeAdd({...classGradeAdd, semester_begin: dateString} as ClassGradeDTO);
    }

    function onEndTime(_date: Dayjs, dateString: string | string[]) {
        setClassGradeAdd({...classGradeAdd, semester_end: dateString} as ClassGradeDTO);
    }

    return (
        <Modal
            open={open}
            title="添加课程表"
            onOk={handleOk}
            onCancel={() => emit(false)}
            footer={
                <div className="flex gap-1 justify-end text-white">
                    <button
                        onClick={() => emit(false)}
                        className="py-1.5 px-4 rounded-lg shadow bg-red-500 hover:bg-red-600 transition"
                    >
                        取消
                    </button>
                    <button
                        onClick={handleOk}
                        className="py-1.5 px-4 rounded-lg shadow bg-sky-500 hover:bg-sky-600 transition"
                    >
                        添加
                    </button>
                </div>
            }
        >
            <div className="grid grid-cols-2 gap-3">
                <input
                    type="text"
                    id="grade_name"
                    placeholder="名字（如：大一第一学期）"
                    value={classGradeAdd.grade_name}
                    onChange={(e) => setClassGradeAdd({
                        ...classGradeAdd,
                        grade_name: e.target.value
                    } as ClassGradeDTO)}
                    className="flex-1 transition w-full rounded-md border-gray-200 shadow-sm sm:text-sm"
                />
                <div className={"flex-1"}>
                    <select
                        name="time"
                        id="time"
                        onChange={(e) => {
                            setClassGradeAdd({...classGradeAdd, class_time_uuid: e.target.value} as ClassGradeDTO)
                        }}
                        className="w-full rounded-md border-gray-200 text-gray-700 sm:text-sm transition"
                    >
                        <option value="">请选择课表时间</option>
                        {timeList.map((item, index) => (
                            <option key={index} value={item.class_time_market_uuid}>{item.name}</option>
                        ))}
                        <option value="system-add">添加+</option>
                    </select>
                </div>
                <DatePicker className={"flex-1 py-1.5 shadow-sm"} onChange={onStartTime}
                            placeholder={"选择开始日期"}/>
                <DatePicker className={"flex-1 py-1.5 shadow-sm"} onChange={onEndTime}
                            placeholder={"选择结束日期"}/>
            </div>
        </Modal>
    );
}
