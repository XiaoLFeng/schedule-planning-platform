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
import {TimeAbleDTO, TimeAddDTO} from "../../models/dto/time_add_dto.ts";
import {DeleteOutlined, PlusOutlined} from "@ant-design/icons";
import dayjs from "dayjs";
import {AddClassTimeAPI} from "../../interface/curriculum_api.ts";

export function TimeAddModal({propOpen, emit, refresh}: Readonly<{
    propOpen: boolean,
    emit: (data: boolean) => void,
    refresh: (data: boolean) => void
}>) {
    const {RangePicker} = DatePicker;

    const [open, setOpen] = useState<boolean>(false);
    const [timeAdd, setTimeAdd] = useState<TimeAddDTO>({
        is_public: true,
        time_able: [{} as TimeAbleDTO] as TimeAbleDTO[]
    } as TimeAddDTO);


    useEffect(() => {
        setOpen(propOpen);
    }, [propOpen]);

    async function handleOk() {
        const getResp = await AddClassTimeAPI(timeAdd);
        if (getResp?.output === "Success") {
            message.success("添加成功");
            emit(false);
            refresh(true);
        } else {
            message.warning(getResp?.error_message);
        }
    }

    function addTimeAble() {
        timeAdd.time_able.push({} as TimeAbleDTO);
        setTimeAdd({...timeAdd});
    }

    return (
        <Modal
            open={open}
            title="新增时间表"
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
            <div className={"grid grid-cols-1 md:grid-cols-2 gap-3"}>
                <div>
                    <label htmlFor="class_name" className="flex">
                        <span className={"text-sm font-medium text-gray-800"}>课程名字</span>
                        <span className={"text-xs text-red-500"}>*</span>
                    </label>
                    <input
                        type="text"
                        id="class_name"
                        value={timeAdd.name}
                        onChange={(event) => setTimeAdd({...timeAdd, name: event.currentTarget.value})}
                        placeholder="广工时间表"
                        className="mt-1 w-full rounded-md border-gray-200 text-gray-800 shadow-sm sm:text-sm"
                    />
                </div>
                <div>
                    <label htmlFor="is_public" className="flex">
                        <span className={"text-sm font-medium text-gray-800"}>是否公开</span>
                        <span className={"text-xs text-red-500"}>*</span>
                    </label>
                    <select
                        name="is_public"
                        id="is_public"
                        value={String(timeAdd.is_public)}
                        onChange={(event) => setTimeAdd({...timeAdd, is_public: Boolean(event.currentTarget.value)})}
                        className="mt-1 w-full rounded-md border-gray-200 text-gray-800 shadow-sm sm:text-sm"
                    >
                        <option value={1}>公开</option>
                        <option value={0}>私有</option>
                    </select>
                </div>
                <label htmlFor="time_able" className="flex">
                    <span className={"text-sm font-medium text-gray-800"}>时间表</span>
                    <span className={"text-xs text-red-500"}>*</span>
                </label>
                {
                    timeAdd.time_able?.map((time, index) => (
                        <div key={"time-" + index} className={"col-span-full flex items-center gap-3"}>
                            <div className={"px-3 h-full rounded-lg bg-sky-100 text-gray-800 shadow flex items-center"}>
                                {index + 1}
                            </div>
                            <RangePicker picker="time"
                                         value={[time.start_time ? dayjs(time.start_time, "HH:mm") : undefined, time.end_time ? dayjs(time.end_time, "HH:mm") : undefined]}
                                         onChange={(dates) => {
                                             if (dates && dates.length === 2) {
                                                 setTimeAdd({
                                                     ...timeAdd,
                                                     time_able: timeAdd.time_able.map((item, i) => {
                                                         if (i === index) {
                                                             return {
                                                                 start_time: dates[0]!.format("HH:mm"),
                                                                 end_time: dates[1]!.format("HH:mm")
                                                             } as TimeAbleDTO;
                                                         } else {
                                                             return item;
                                                         }
                                                     })
                                                 });
                                             }
                                         }}
                                         className={"rounded-md border-gray-200 text-gray-800 shadow-sm sm:text-sm flex-1 h-full"}/>
                            {
                                timeAdd.time_able.length > 1 ? (
                                    <button onClick={() => {
                                        timeAdd.time_able.splice(index, 1);
                                        setTimeAdd({...timeAdd});
                                    }}
                                         className={"px-2.5 h-full rounded-lg bg-red-100 text-gray-800 shadow flex items-center transition hover:bg-red-200 hover:scale-105"}>
                                        <DeleteOutlined/>
                                    </button>
                                ) : (
                                    <div
                                        className={"px-2.5 h-full rounded-lg bg-gray-100 text-gray-800 shadow flex items-center"}>
                                        <DeleteOutlined/>
                                    </div>
                                )
                            }
                        </div>
                    ))
                }
                <div className={"col-span-full flex justify-end"}>
                    <button onClick={addTimeAble}
                         className={"transition p-3 bg-emerald-500 hover:bg-emerald-600 rounded-lg text-white flex item-center"}>
                        <PlusOutlined/>
                    </button>
                </div>
            </div>
        </Modal>
    );
}
